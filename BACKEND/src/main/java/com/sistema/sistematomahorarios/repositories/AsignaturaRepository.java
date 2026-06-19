package com.sistema.sistematomahorarios.repositories;

import com.sistema.sistematomahorarios.entities.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Integer> {

    List<Asignatura> findByDepartamentoIdDepartamento(Integer idDepartamento);

}
