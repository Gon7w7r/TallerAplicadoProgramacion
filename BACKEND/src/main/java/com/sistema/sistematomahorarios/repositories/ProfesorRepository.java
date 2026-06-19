package com.sistema.sistematomahorarios.repositories;

import com.sistema.sistematomahorarios.entities.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesorRepository extends JpaRepository<Profesor, Integer> {
    Profesor findByUsuarioRut(String rut);
}