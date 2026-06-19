package com.sistema.sistematomahorarios.dto;

public class DemandaDTO {
    private String  nombreAsignatura;
    private long    totalCupos;
    private long    totalInscritos;
    private double  ratio;

    public DemandaDTO(String nombreAsignatura, long totalCupos, long totalInscritos) {
        this.nombreAsignatura = nombreAsignatura;
        this.totalCupos       = totalCupos;
        this.totalInscritos   = totalInscritos;
        this.ratio            = totalCupos > 0 ? (double) totalInscritos / totalCupos : 0;
    }

    public String  getNombreAsignatura() { return nombreAsignatura; }
    public long    getTotalCupos()       { return totalCupos; }
    public long    getTotalInscritos()   { return totalInscritos; }
    public double  getRatio()            { return ratio; }
}