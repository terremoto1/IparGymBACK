package com.ipargym.dtos;

/** Token de acceso devuelto tras autenticar correctamente al usuario. */
public record UsuarioAutenticarSalidaDto(
        String accessToken,
        String tokenType) {
}
