package com.sistema.sistematomahorarios.repositories;

import com.sistema.sistematomahorarios.dto.ItemCountDTO;
import com.sistema.sistematomahorarios.entities.AlumnoCarrera;
import com.sistema.sistematomahorarios.entities.AlumnoCarreraId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AlumnoCarreraRepository extends JpaRepository<AlumnoCarrera, AlumnoCarreraId> {
    List<AlumnoCarrera> findByAlumnoIdAlumno(Integer idAlumno);

    @Query("SELECT new com.sistema.sistematomahorarios.dto.ItemCountDTO(ac.carrera.nombre, COUNT(ac.alumno)) FROM AlumnoCarrera ac GROUP BY ac.carrera.nombre")
    List<ItemCountDTO> countAlumnosPorCarrera();
}