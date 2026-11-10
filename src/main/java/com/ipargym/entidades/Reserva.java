package com.ipargym.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas", uniqueConstraints = @UniqueConstraint(name = "uk_reserva_usuario_actividad", columnNames = {
        "usuario_id", "actividad_id" }))
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "actividad_id", nullable = false)
    private Actividad actividad;
    @Column(nullable = false)
    private LocalDateTime fechaReserva;

    protected Reserva() {
    }

    public Reserva(Usuario usuario, Actividad actividad, LocalDateTime fechaReserva) {
        this.usuario = usuario;
        this.actividad = actividad;
        this.fechaReserva = fechaReserva;
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }
}
