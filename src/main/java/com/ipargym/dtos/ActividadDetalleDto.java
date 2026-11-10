package com.ipargym.dtos;

import java.time.LocalDateTime;

/** Detalle de una actividad, incluyendo sus plazas disponibles. */
public record ActividadDetalleDto(
        Long id,
        String nombre,
        String descripcion,
        String monitor,
        LocalDateTime fechaHora,
        int duracionMinutos,
        int plazas,
        int plazasLibres) {
}
