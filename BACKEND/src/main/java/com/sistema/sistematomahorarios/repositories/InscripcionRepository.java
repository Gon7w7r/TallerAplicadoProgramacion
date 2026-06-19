package com.sistema.sistematomahorarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.sistema.sistematomahorarios.entities.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {
    List<Inscripcion> findByAlumnoIdAlumno(Integer idAlumno);

    boolean existsByAlumnoIdAlumnoAndSeccionIdSeccion(Integer idAlumno, Integer idSeccion);

    long countBySeccionIdSeccion(Integer idSeccion);

    boolean existsByAlumnoIdAlumnoAndSeccionAsignaturaIdAsignatura(
            Integer idAlumno,
            Integer idAsignatura);

    @Query("SELECT COUNT(DISTINCT i.alumno.idAlumno) FROM Inscripcion i")
    long countAlumnosConInscripcion();


    @Modifying
    @Transactional
    @Query("DELETE FROM Inscripcion i WHERE i.seccion.idSeccion = :id")
    void deleteBySeccionIdSeccion(Integer id);

}