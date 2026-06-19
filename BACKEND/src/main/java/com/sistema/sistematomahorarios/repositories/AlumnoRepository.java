package com.sistema.sistematomahorarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.sistematomahorarios.entities.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {
    Alumno findByUsuarioRut(String rut);


}
