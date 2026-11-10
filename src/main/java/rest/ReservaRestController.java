package com.ipargym.rest;

import com.ipargym.dtos.ReservaCrearEntradaDto;
import com.ipargym.dtos.ReservaSalidaDto;
import com.ipargym.servicios.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v2/reservas")
public class ReservaRestController {
    private final ReservaService reservaService;

    public ReservaRestController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaSalidaDto> reservar(
            @Valid @RequestBody ReservaCrearEntradaDto entrada,
            @AuthenticationPrincipal Jwt jwt) {
        ReservaSalidaDto reserva = reservaService.reservar(entrada.idActividad(), jwt.getSubject());
        return ResponseEntity.created(URI.create("/api/v2/reservas/" + reserva.id())).body(reserva);
    }

    @GetMapping
    public List<ReservaSalidaDto> misReservas(@AuthenticationPrincipal Jwt jwt) {
        return reservaService.misReservas(jwt.getSubject());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        reservaService.cancelar(id, jwt.getSubject());
        return ResponseEntity.noContent().build();
    }
}
