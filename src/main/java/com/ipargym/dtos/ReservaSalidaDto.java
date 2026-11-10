package com.ipargym.dtos;

import java.time.LocalDateTime;

/** Datos de una reserva que se devuelven al cliente; no expone entidades JPA. */
public record ReservaSalidaDto(
        Long id,
        Long idActividad,
        String nombreActividad,
        LocalDateTime fechaHora,
        LocalDateTime fechaReserva) {
}
