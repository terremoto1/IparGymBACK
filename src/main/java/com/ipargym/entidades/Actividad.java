package com.ipargym.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "actividades")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 40)
    private String nombre;
    @Column(length = 1000)
    private String descripcion;
    @Column(length = 100)
    private String monitor;
    @Column(nullable = false)
    private LocalDateTime fechaHora;
    @Column(nullable = false)
    private int duracionMinutos;
    @Column(nullable = false)
    private int plazas;
    @OneToMany(mappedBy = "actividad")
    private List<Reserva> reservas = new ArrayList<>();

    protected Actividad() {
    }

    public Actividad(String nombre, String descripcion, String monitor, LocalDateTime fechaHora, int duracionMinutos,
            int plazas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.monitor = monitor;
        this.fechaHora = fechaHora;
        this.duracionMinutos = duracionMinutos;
        this.plazas = plazas;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getMonitor() {
        return monitor;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public int getPlazas() {
        return plazas;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }
}
