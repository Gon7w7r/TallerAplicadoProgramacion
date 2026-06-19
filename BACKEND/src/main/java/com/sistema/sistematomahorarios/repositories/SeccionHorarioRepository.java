package com.sistema.sistematomahorarios.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sistema.sistematomahorarios.entities.SeccionHorario;
import com.sistema.sistematomahorarios.entities.SeccionHorarioId;

import java.util.List;

public interface SeccionHorarioRepository extends JpaRepository<SeccionHorario,SeccionHorarioId> {
    List<SeccionHorario> findBySeccionIdSeccion(Integer idSeccion);

    @Modifying
    @Query("DELETE FROM SeccionHorario sh WHERE sh.seccion.idSeccion = :id")
    void deleteBySeccionIdSeccion(Integer id);

}
