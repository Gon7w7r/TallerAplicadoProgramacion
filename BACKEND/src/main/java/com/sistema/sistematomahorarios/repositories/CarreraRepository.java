package com.sistema.sistematomahorarios.repositories;

import com.sistema.sistematomahorarios.entities.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarreraRepository extends JpaRepository<Carrera, Integer> {

    List<Carrera> findByTipo(String tipo);

}
