package com.sistema.sistematomahorarios.dto;

public class InscripcionDetalleDTO {
    private Integer idAsignatura;
    private String nombreAsignatura;
    private Integer idSeccion;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String sala;

    public InscripcionDetalleDTO(
            Integer idAsignatura,
            String nombreAsignatura,
            Integer idSeccion,
            String diaSemana,
            String horaInicio,
            String horaFin,
            String sala
    ) {
        this.idAsignatura = idAsignatura;
        this.nombreAsignatura = nombreAsignatura;
        this.idSeccion = idSeccion;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.sala = sala;
    }

    public Integer getIdAsignatura() { return idAsignatura; }
    public String getNombreAsignatura() { return nombreAsignatura; }
    public Integer getIdSeccion() { return idSeccion; }
    public String getDiaSemana() { return diaSemana; }
    public String getHoraInicio() { return horaInicio; }
    public String getHoraFin() { return horaFin; }
    public String getSala() { return sala; }
}