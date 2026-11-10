package com.ipargym.modelos;

import com.ipargym.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/** Acceso y consultas persistentes para las reservas. */
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUsuarioEmailOrderByFechaReservaDesc(String email);

    boolean existsByUsuarioEmailAndActividadId(String email, Long actividadId);

    long countByActividadId(Long actividadId);

    Optional<Reserva> findByIdAndUsuarioEmail(Long id, String email);
}
