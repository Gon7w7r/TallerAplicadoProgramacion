package com.sistema.sistematomahorarios.entities;

import java.io.Serializable;
import java.util.Objects;

public class SeccionHorarioId implements Serializable {

    private Integer seccion;
    private Integer horario;

    public SeccionHorarioId() {}

    public SeccionHorarioId(Integer seccion, Integer horario){
        this.seccion = seccion;
        this.horario = horario;
    }



    //equals y HashCode

    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if(!(o instanceof SeccionHorarioId)) return false;
        SeccionHorarioId that = (SeccionHorarioId)o;
        return Objects.equals(seccion, that.seccion) && Objects.equals(horario, that.horario);
    }

    @Override
    public int hashCode(){
        return Objects.hash(seccion,horario);
    }


}
