package com.sistema.sistematomahorarios.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name="inscripcion")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInscripcion;

    private LocalDateTime fecha;

    // 🔗 Relaciones

    @ManyToOne
    @JoinColumn(name = "alumno_id_alumno", nullable = false)
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "seccion_id_seccion", nullable = false)
    private Seccion seccion;

    @ManyToOne
    @JoinColumn(name = "periodo_id_periodo", nullable = false)  // ✅ nombre correcto
    private Periodo periodo;


     // Getters and Setters

    public Integer getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(Integer idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

}