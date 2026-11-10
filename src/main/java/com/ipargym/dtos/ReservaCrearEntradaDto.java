package com.ipargym.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/** Datos recibidos para reservar una actividad. */
public record ReservaCrearEntradaDto(
        @NotNull @Positive Long idActividad) {
}
