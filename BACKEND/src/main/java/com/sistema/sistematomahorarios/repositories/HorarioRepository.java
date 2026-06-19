package com.sistema.sistematomahorarios.repositories;

import com.sistema.sistematomahorarios.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {
}