package com.ipargym.modelos;

import com.ipargym.entidades.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/** Acceso y consultas persistentes para las actividades. */
public interface ActividadRepository extends JpaRepository<Actividad, Long> {

    @Query("select a from Actividad a where a.fechaHora > :ahora order by a.fechaHora asc")
    List<Actividad> buscarFuturasOrdenadas(@Param("ahora") LocalDateTime ahora);
}
