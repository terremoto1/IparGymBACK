package com.ipargym.servicios;

import com.ipargym.dtos.ReservaSalidaDto;
import com.ipargym.entidades.Actividad;
import com.ipargym.entidades.Reserva;
import com.ipargym.entidades.Usuario;
import com.ipargym.modelos.ActividadRepository;
import com.ipargym.modelos.ReservaRepository;
import com.ipargym.modelos.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/** Casos de uso de reserva, consulta y cancelación de plazas. */
@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final ActividadRepository actividadRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaService(
            ReservaRepository reservaRepository,
            ActividadRepository actividadRepository,
            UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.actividadRepository = actividadRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /** Reserva una plaza para el usuario identificado por el email autenticado. */
    @Transactional
    public ReservaSalidaDto reservar(Long idActividad, String emailUsuario) {
        Actividad actividad = actividadRepository.findById(idActividad)
                .orElseThrow(() -> new ReservaException("La actividad no existe", HttpStatus.NOT_FOUND));

        if (!actividad.getFechaHora().isAfter(LocalDateTime.now())) {
            throw new ReservaException("La actividad ya se ha celebrado", HttpStatus.CONFLICT);
        }

        if (reservaRepository.existsByUsuarioEmailAndActividadId(emailUsuario, idActividad)) {
            throw new ReservaException("Ya tienes una reserva para esta actividad", HttpStatus.CONFLICT);
        }

        if (reservaRepository.countByActividadId(idActividad) >= actividad.getPlazas()) {
            throw new ReservaException("No quedan plazas disponibles", HttpStatus.CONFLICT);
        }

        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new ReservaException("El usuario no existe", HttpStatus.NOT_FOUND));

        Reserva reserva = new Reserva(usuario, actividad, LocalDateTime.now());
        return aSalidaDto(reservaRepository.save(reserva));
    }

    /** Devuelve únicamente las reservas del usuario autenticado. */
    @Transactional(readOnly = true)
    public List<ReservaSalidaDto> misReservas(String emailUsuario) {
        return reservaRepository.findByUsuarioEmailOrderByFechaReservaDesc(emailUsuario).stream()
                .map(this::aSalidaDto)
                .toList();
    }

    /** Cancela una reserva únicamente si pertenece al usuario autenticado. */
    @Transactional
    public void cancelar(Long idReserva, String emailUsuario) {
        Reserva reserva = reservaRepository.findByIdAndUsuarioEmail(idReserva, emailUsuario)
                .orElseThrow(() -> new ReservaException("La reserva no existe", HttpStatus.NOT_FOUND));
        reservaRepository.delete(reserva);
    }

    private ReservaSalidaDto aSalidaDto(Reserva reserva) {
        return new ReservaSalidaDto(
                reserva.getId(),
                reserva.getActividad().getId(),
                reserva.getActividad().getNombre(),
                reserva.getActividad().getFechaHora(),
                reserva.getFechaReserva());
    }
}
