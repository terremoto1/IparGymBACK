package com.ipargym.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/** Datos recibidos al crear una actividad desde la API de administración. */
public record ActividadCrearEntradaDto(
        @NotBlank @Size(max = 40) String nombre,
        @Size(max = 1000) String descripcion,
        @Size(max = 100) String monitor,
        @NotNull @Future LocalDateTime fechaHora,
        @Min(1) int duracionMinutos,
        @Min(1) int plazas) {
}
