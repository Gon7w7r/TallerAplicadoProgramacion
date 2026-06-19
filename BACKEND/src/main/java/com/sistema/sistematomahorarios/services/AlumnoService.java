package com.sistema.sistematomahorarios.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import com.sistema.sistematomahorarios.dto.AsignaturaDisponibleDTO;
import com.sistema.sistematomahorarios.dto.SeccionDisponibleDTO;
import com.sistema.sistematomahorarios.entities.AlumnoCarrera;
import com.sistema.sistematomahorarios.entities.Asignatura;
import com.sistema.sistematomahorarios.entities.AsignaturaCarrera;
import com.sistema.sistematomahorarios.entities.Seccion;
import com.sistema.sistematomahorarios.repositories.AlumnoCarreraRepository;
import com.sistema.sistematomahorarios.repositories.AsignaturaCarerraRepository;
import com.sistema.sistematomahorarios.repositories.SeccionRepository;
import org.springframework.stereotype.Service;

@Service
public class AlumnoService {
    @Autowired
    private AlumnoCarreraRepository alumnoCarreraRepository;
    @Autowired
    private AsignaturaCarerraRepository asignaturaCarerraRepository;
    @Autowired
    private SeccionRepository seccionRepository;


    public List<AsignaturaDisponibleDTO> obtenerAsignaturasDisponibles(Integer idAlumno) {

        List<AsignaturaDisponibleDTO> resultado = new ArrayList<>();

        // 1. Obtener carreras del alumno
        List<AlumnoCarrera> carrerasAlumno =
                alumnoCarreraRepository.findByAlumnoIdAlumno(idAlumno);

        // 2. Recorrer carreras
        for (AlumnoCarrera ac : carrerasAlumno) {

            Integer idCarrera = ac.getCarrera().getIdCarrera();

            // 3. Obtener asignaturas de la carrera
            List<AsignaturaCarrera> asignaturasCarrera =
                    asignaturaCarerraRepository.findByCarreraIdCarrera(idCarrera);

            // 4. Recorrer asignaturas
            for (AsignaturaCarrera asignaturaCarrera : asignaturasCarrera) {

                Asignatura asignatura = asignaturaCarrera.getAsignatura();

                // 5. Obtener secciones
                List<Seccion> secciones =
                        seccionRepository.findByAsignaturaIdAsignatura(
                                asignatura.getIdAsignatura()
                        );

                // 6. Convertir secciones a DTO
                List<SeccionDisponibleDTO> seccionesDTO = secciones.stream()
                        .map(seccion -> new SeccionDisponibleDTO(
                                seccion.getIdSeccion(),
                                seccion.getProfesor().getUsuario().getNombre(),
                                seccion.getSala().getNombre(),
                                seccion.getCupos()
                        ))
                        .toList();

                // 7. Crear DTO asignatura
                AsignaturaDisponibleDTO dto =
                        new AsignaturaDisponibleDTO(
                                asignatura.getIdAsignatura(),
                                asignatura.getNombre(),
                                seccionesDTO
                        );

                resultado.add(dto);
            }
        }

        return resultado;
    }
    

}
