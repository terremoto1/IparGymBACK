package com.ipargym.rest;

import com.ipargym.dtos.ActividadCrearEntradaDto;
import com.ipargym.dtos.ActividadDetalleDto;
import com.ipargym.dtos.ActividadListadoDto;
import com.ipargym.servicios.ActividadService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v2/actividades")
public class ActividadRestController {
    private final ActividadService actividadService;

    public ActividadRestController(ActividadService actividadService) {
        this.actividadService = actividadService;
    }

    @GetMapping
    public List<ActividadListadoDto> listarFuturas() {
        return actividadService.listarFuturas();
    }

    @GetMapping("/{id}")
    public ActividadDetalleDto obtenerDetalle(@PathVariable Long id) {
        return actividadService.obtenerDetalle(id);
    }

    /** Reto opcional: el rol se valida a partir de la autoridad del JWT. */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ActividadDetalleDto> crear(@Valid @RequestBody ActividadCrearEntradaDto entrada) {
        ActividadDetalleDto creada = actividadService.crear(entrada);
        return ResponseEntity.created(URI.create("/api/v2/actividades/" + creada.id())).body(creada);
    }
}
