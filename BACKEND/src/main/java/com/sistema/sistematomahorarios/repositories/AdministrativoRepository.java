package com.sistema.sistematomahorarios.repositories;

import com.sistema.sistematomahorarios.entities.Administrativo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrativoRepository extends JpaRepository<Administrativo, Integer> {
    Administrativo findByUsuarioRut(String rut);
}