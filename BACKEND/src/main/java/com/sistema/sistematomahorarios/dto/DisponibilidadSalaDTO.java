package com.sistema.sistematomahorarios.dto;

public class DisponibilidadSalaDTO {
    private String  nombreSala;
    private String  horaInicio;
    private String  horaFin;
    private boolean ocupada;
    private String  nombreAsignatura;

    public DisponibilidadSalaDTO(
        String nombreSala,
        String horaInicio,
        String horaFin,
        boolean ocupada,
        String nombreAsignatura
    ) {
        this.nombreSala       = nombreSala;
        this.horaInicio       = horaInicio;
        this.horaFin          = horaFin;
        this.ocupada          = ocupada;
        this.nombreAsignatura = nombreAsignatura;
    }

    public String  getNombreSala()       { return nombreSala; }
    public String  getHoraInicio()       { return horaInicio; }
    public String  getHoraFin()          { return horaFin; }
    public boolean isOcupada()           { return ocupada; }
    public String  getNombreAsignatura() { return nombreAsignatura; }
}