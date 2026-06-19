package com.sistema.sistematomahorarios.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "sala")
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSala;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipo;//virtual o presencial

    @ManyToOne
    @JoinColumn(name = "sede_id_sede", nullable = false)
    private Sede sede;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(name = "tiene_pc", nullable = false)
    private Boolean tienePc = false;

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Integer getCapacidad() { 
        return capacidad; 
    }

    public void setCapacidad(Integer capacidad) { 
        this.capacidad = capacidad; 
    }

    public Boolean getTienePc() { 
        return tienePc; 
    }

    public void setTienePc(Boolean tienePc) { 
        this.tienePc = tienePc; 
    }

}