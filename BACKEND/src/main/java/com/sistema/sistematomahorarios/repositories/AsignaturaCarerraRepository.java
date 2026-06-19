package com.sistema.sistematomahorarios.repositories;

import com.sistema.sistematomahorarios.entities.AsignaturaCarrera;
import com.sistema.sistematomahorarios.entities.AsignaturaCarreraId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface AsignaturaCarerraRepository extends JpaRepository<AsignaturaCarrera, AsignaturaCarreraId> {
    List<AsignaturaCarrera> findByCarreraIdCarrera(Integer idCarrera);

    void deleteByCarreraIdCarreraAndAsignaturaIdAsignatura(Integer idCarrera, Integer idAsignatura);
    
}