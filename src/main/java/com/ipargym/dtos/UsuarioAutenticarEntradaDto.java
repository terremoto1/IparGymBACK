package com.ipargym.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/** Credenciales recibidas para iniciar sesión. */
public record UsuarioAutenticarEntradaDto(
        @NotBlank @Email String email,
        @NotBlank String password) {
}
