package com.ipargym.servicios;

import org.springframework.http.HttpStatus;

/** Excepción de reglas de reserva; el controlador traduce el estado a HTTP. */
public class ReservaException extends RuntimeException {
    private final HttpStatus estadoHttp;

    public ReservaException(String mensaje, HttpStatus estadoHttp) {
        super(mensaje);
        this.estadoHttp = estadoHttp;
    }

    public HttpStatus getEstadoHttp() {
        return estadoHttp;
    }
}
