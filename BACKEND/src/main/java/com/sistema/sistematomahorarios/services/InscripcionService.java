package com.sistema.sistematomahorarios.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.sistematomahorarios.dto.InscripcionDetalleDTO;
import com.sistema.sistematomahorarios.dto.InscripcionResponseDTO;
import com.sistema.sistematomahorarios.entities.Alumno;
import com.sistema.sistematomahorarios.entities.Inscripcion;
import com.sistema.sistematomahorarios.entities.Seccion;
import com.sistema.sistematomahorarios.entities.SeccionHorario;
import com.sistema.sistematomahorarios.repositories.AlumnoRepository;
import com.sistema.sistematomahorarios.repositories.InscripcionRepository;
import com.sistema.sistematomahorarios.repositories.PeriodoRepository;
import com.sistema.sistematomahorarios.repositories.SeccionHorarioRepository;
import com.sistema.sistematomahorarios.repositories.SeccionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private SeccionHorarioRepository seccionHorarioRepository;

    @Autowired
    private SeccionRepository seccionRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private PeriodoRepository periodoRepository;

    @Autowired
    private EmailService emailService;

    // ── Inscribir una sección ─────────────────────────────────────────────
    public String inscribir(Integer idAlumno, Integer idSeccion, Integer idPeriodo) {
        

        if (inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionIdSeccion(idAlumno, idSeccion)) {
            return "El alumno ya está inscrito en esta sección";
        }

        long inscritos = inscripcionRepository.countBySeccionIdSeccion(idSeccion);
        Seccion seccion = seccionRepository.findById(idSeccion).orElseThrow();

        if (inscripcionRepository.existsByAlumnoIdAlumnoAndSeccionAsignaturaIdAsignatura(
                idAlumno, seccion.getAsignatura().getIdAsignatura())) {
            return "El alumno ya está inscrito en esta asignatura";
        }

        if (inscritos >= seccion.getCupos()) {
            return "No hay cupos disponibles";
        }

        if (tieneChoqueHorario(idAlumno, idSeccion)) {
            return "Existe choque de horario";
        }

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setAlumno(alumnoRepository.findById(idAlumno).orElseThrow());
        inscripcion.setSeccion(seccion);
        inscripcion.setPeriodo(periodoRepository.findById(idPeriodo).orElseThrow());
        inscripcion.setFecha(LocalDateTime.now());
        inscripcionRepository.save(inscripcion);

        return "Inscripción exitosa";
    }

    // ── Inscribir múltiples secciones ─────────────────────────────────────
    public List<String> inscribirMultiple(
            Integer idAlumno,
            List<Integer> secciones,
            Integer idPeriodo
    ) {
        List<String> resultados = new ArrayList<>();
        boolean huboExitosa = false;


        for (Integer idSeccion : secciones) {
            String resultado = inscribir(idAlumno, idSeccion, idPeriodo);
            resultados.add("Sección " + idSeccion + ": " + resultado);

            if (resultado.equals("Inscripción exitosa")) {
                huboExitosa = true;
            }
        }

        if (huboExitosa) {
            enviarCorreo(idAlumno, construirHorarioCompleto(idAlumno));
        }

        return resultados;
    }

    // ── Actualizar horario (borra y reinscribe) ───────────────────────────
    @Transactional
    public List<String> actualizarHorario(
            Integer idAlumno,
            List<Integer> seccionesNuevas,
            Integer idPeriodo
    ) {
        List<Inscripcion> actuales = inscripcionRepository.findByAlumnoIdAlumno(idAlumno);
        boolean huboExitosa = false;  // ← aquí
        inscripcionRepository.deleteAllInBatch(actuales);
        inscripcionRepository.flush();

        List<String> resultados = new ArrayList<>();

        for (Integer idSeccion : seccionesNuevas) {
            String resultado = inscribir(idAlumno, idSeccion, idPeriodo);
            resultados.add("Sección " + idSeccion + ": " + resultado);

            if (resultado.equals("Inscripción exitosa")) {
                huboExitosa = true;
            }
        }

        if (huboExitosa) {
            enviarCorreo(idAlumno, construirHorarioCompleto(idAlumno));
        }

        return resultados;
    }

    // ── Obtener inscripciones de un alumno ────────────────────────────────
    public List<InscripcionResponseDTO> obtenerPorAlumno(Integer idAlumno) {
        return inscripcionRepository.findByAlumnoIdAlumno(idAlumno)
                .stream()
                .map(i -> new InscripcionResponseDTO(
                        i.getSeccion().getAsignatura().getIdAsignatura(),
                        i.getSeccion().getAsignatura().getNombre(),
                        i.getSeccion().getIdSeccion()
                ))
                .toList();
    }

    // ── Helpers ───────────────────────────────────────────────────────────

    // Construye los detalles de una sección para el PDF/correo
   private List<InscripcionDetalleDTO> construirHorarioCompleto(Integer idAlumno) {
    List<InscripcionDetalleDTO> detalles = new ArrayList<>();
    List<Inscripcion> inscripciones = inscripcionRepository.findByAlumnoIdAlumno(idAlumno);

    for (Inscripcion ins : inscripciones) {
        Seccion seccion = ins.getSeccion();
        List<SeccionHorario> horarios = seccionHorarioRepository
            .findBySeccionIdSeccion(seccion.getIdSeccion());

        for (SeccionHorario sh : horarios) {
            detalles.add(new InscripcionDetalleDTO(
                seccion.getAsignatura().getIdAsignatura(),
                seccion.getAsignatura().getNombre(),
                seccion.getIdSeccion(),
                sh.getHorario().getDiaSemana(),
                sh.getHorario().getHoraInicio().toString(),
                sh.getHorario().getHoraFin().toString(),
                seccion.getSala().getNombre()
            ));
        }
    }
    return detalles;
}

    // Obtiene email y nombre del alumno y llama al EmailService
    private void enviarCorreo(Integer idAlumno, List<InscripcionDetalleDTO> detalles) {
        Alumno alumno = alumnoRepository.findById(idAlumno).orElseThrow();
        String email  = alumno.getUsuario().getEmail();
        String nombre = alumno.getUsuario().getNombre();
        emailService.enviarCorreoInscripcion(email, nombre, detalles);
    }

    private boolean tieneChoqueHorario(Integer idAlumno, Integer idSeccionNueva) {
        List<SeccionHorario> nueva = seccionHorarioRepository.findBySeccionIdSeccion(idSeccionNueva);
        List<Inscripcion> actuales = inscripcionRepository.findByAlumnoIdAlumno(idAlumno);

        for (Inscripcion insc : actuales) {
            List<SeccionHorario> existentes =
                    seccionHorarioRepository.findBySeccionIdSeccion(insc.getSeccion().getIdSeccion());
            for (SeccionHorario h1 : nueva) {
                for (SeccionHorario h2 : existentes) {
                    if (esMismoDia(h1, h2) && hayCruceHoras(h1, h2)) return true;
                }
            }
        }
        return false;
    }

    private boolean esMismoDia(SeccionHorario h1, SeccionHorario h2) {
        return h1.getHorario().getDiaSemana().equals(h2.getHorario().getDiaSemana());
    }

    private boolean hayCruceHoras(SeccionHorario h1, SeccionHorario h2) {
        var inicio1 = h1.getHorario().getHoraInicio();
        var fin1    = h1.getHorario().getHoraFin();
        var inicio2 = h2.getHorario().getHoraInicio();
        var fin2    = h2.getHorario().getHoraFin();
        return inicio1.isBefore(fin2) && fin1.isAfter(inicio2);
    }
}