package com.sistema.sistematomahorarios.repositories;

import com.sistema.sistematomahorarios.dto.DemandaDTO;
import com.sistema.sistematomahorarios.dto.ItemCountDTO;
import org.springframework.data.jpa.repository.Query;
import com.sistema.sistematomahorarios.entities.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeccionRepository extends JpaRepository<Seccion, Integer> {

    List<Seccion> findByAsignaturaIdAsignatura(Integer idAsignatura);

    List<Seccion> findByProfesorIdProfesor(Integer idProfesor);

    @Query("SELECT new com.sistema.sistematomahorarios.dto.ItemCountDTO(s.asignatura.nombre, COUNT(s)) FROM Seccion s GROUP BY s.asignatura.nombre")
    List<ItemCountDTO> countSeccionesPorAsignatura();

    @Query("""
                SELECT new com.sistema.sistematomahorarios.dto.DemandaDTO(
                    s.asignatura.nombre,
                    SUM(s.cupos),
                    COUNT(i)
                )
                FROM Seccion s
                LEFT JOIN Inscripcion i ON i.seccion.idSeccion = s.idSeccion
                GROUP BY s.asignatura.nombre
                ORDER BY (COUNT(i) * 1.0 / SUM(s.cupos)) DESC
            """)
    List<DemandaDTO> getDemandaPorAsignatura();

}