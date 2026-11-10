package com.ipargym.servicios;

import com.ipargym.dtos.ActividadCrearEntradaDto;
import com.ipargym.dtos.ActividadDetalleDto;
import com.ipargym.dtos.ActividadListadoDto;
import com.ipargym.entidades.Actividad;
import com.ipargym.modelos.ActividadRepository;
import com.ipargym.modelos.ReservaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/** Casos de uso de consulta de actividades. */
@Service
public class ActividadService {
    private final ActividadRepository actividadRepository;
    private final ReservaRepository reservaRepository;

    public ActividadService(ActividadRepository actividadRepository, ReservaRepository reservaRepository) {
        this.actividadRepository = actividadRepository;
        this.reservaRepository = reservaRepository;
    }

    /** Devuelve las actividades futuras por fecha, con las plazas libres calculadas. */
    @Transactional(readOnly = true)
    public List<ActividadListadoDto> listarFuturas() {
        LocalDateTime ahora = LocalDateTime.now();
        return actividadRepository.buscarFuturasOrdenadas(ahora).stream()
                .map(this::aListadoDto)
                .toList();
    }

    /** Crea una actividad nueva desde los datos validados del DTO. */
    @Transactional
    public ActividadDetalleDto crear(ActividadCrearEntradaDto entrada) {
        Actividad actividad = new Actividad(
                entrada.nombre(),
                entrada.descripcion(),
                entrada.monitor(),
                entrada.fechaHora(),
                entrada.duracionMinutos(),
                entrada.plazas());
        return aDetalleDto(actividadRepository.save(actividad));
    }
    /** Devuelve el detalle de una actividad o 404 si no existe. */
    @Transactional(readOnly = true)
    public ActividadDetalleDto obtenerDetalle(Long id) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new ReservaException("La actividad no existe", HttpStatus.NOT_FOUND));
        return aDetalleDto(actividad);
    }

    /** Devuelve todas las actividades para la gestión de administración. */
    @Transactional(readOnly = true)
    public List<ActividadDetalleDto> listarTodas() {
        return actividadRepository.findAll().stream()
                .sorted(java.util.Comparator.comparing(Actividad::getFechaHora))
                .map(this::aDetalleDto)
                .toList();
    }

    /** Actualiza una actividad existente con los datos validados del DTO. */
    @Transactional
    public ActividadDetalleDto actualizar(Long id, ActividadCrearEntradaDto entrada) {
        Actividad actividad = actividadRepository.findById(id)
                .orElseThrow(() -> new ReservaException("La actividad no existe", HttpStatus.NOT_FOUND));
        actividad.setNombre(entrada.nombre());
        actividad.setDescripcion(entrada.descripcion());
        actividad.setMonitor(entrada.monitor());
        actividad.setFechaHora(entrada.fechaHora());
        actividad.setDuracionMinutos(entrada.duracionMinutos());
        actividad.setPlazas(entrada.plazas());
        return aDetalleDto(actividadRepository.save(actividad));
    }
    private ActividadListadoDto aListadoDto(Actividad actividad) {
        return new ActividadListadoDto(
                actividad.getId(),
                actividad.getNombre(),
                actividad.getDescripcion(),
                actividad.getMonitor(),
                actividad.getFechaHora(),
                actividad.getDuracionMinutos(),
                actividad.getPlazas(),
                calcularPlazasLibres(actividad));
    }

    private ActividadDetalleDto aDetalleDto(Actividad actividad) {
        return new ActividadDetalleDto(
                actividad.getId(),
                actividad.getNombre(),
                actividad.getDescripcion(),
                actividad.getMonitor(),
                actividad.getFechaHora(),
                actividad.getDuracionMinutos(),
                actividad.getPlazas(),
                calcularPlazasLibres(actividad));
    }

    private int calcularPlazasLibres(Actividad actividad) {
        long reservasActuales = reservaRepository.countByActividadId(actividad.getId());
        return Math.max(0, actividad.getPlazas() - (int) reservasActuales);
    }
}


