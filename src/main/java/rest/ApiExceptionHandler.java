package com.ipargym.rest;

import com.ipargym.servicios.ReservaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ReservaException.class)
    public ResponseEntity<ProblemDetail> reserva(ReservaException ex) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(ex.getEstadoHttp(), ex.getMessage());
        problema.setTitle(ex.getEstadoHttp().getReasonPhrase());
        problema.setType(URI.create("https://ipargym.local/problemas/reserva"));
        return ResponseEntity.status(ex.getEstadoHttp()).body(problema);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ProblemDetail> credencialesIncorrectas() {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED, "Email o contraseña incorrectos");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problema);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> peticionInvalida(MethodArgumentNotValidException ex) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Los datos enviados no son válidos");
        return ResponseEntity.badRequest().body(problema);
    }
}
