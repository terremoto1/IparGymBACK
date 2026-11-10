package com.ipargym.dtos;

import java.time.LocalDateTime;

/** Datos que se muestran en la agenda pública de actividades futuras. */
public record ActividadListadoDto(
        Long id,
        String nombre,
        String descripcion,
        String monitor,
        LocalDateTime fechaHora,
        int duracionMinutos,
        int plazas,
        int plazasLibres) {
}
