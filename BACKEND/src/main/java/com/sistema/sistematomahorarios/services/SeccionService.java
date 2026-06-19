package com.sistema.sistematomahorarios.services;

import com.sistema.sistematomahorarios.dto.SeccionRequestDTO;
import com.sistema.sistematomahorarios.entities.Seccion;
import com.sistema.sistematomahorarios.entities.SeccionHorario;
import com.sistema.sistematomahorarios.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SeccionService {

    @Autowired
    private SeccionRepository seccionRepository;
    @Autowired
    private InscripcionRepository inscripcionRepository;
    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private SeccionHorarioRepository seccionHorarioRepository;
    @Autowired
    private AsignaturaRepository asignaturaRepository;
    @Autowired
    private ProfesorRepository profesorRepository;
    @Autowired
    private SalaRepository salaRepository;
    @Autowired
    private SedeRepository sedeRepository;
    @Autowired
    private ModalidadRepository modalidadRepository;
    @Autowired
    private JornadaRepository jornadaRepository;

    public List<Seccion> listarTodas() {
        return seccionRepository.findAll();
    }

    public Optional<Seccion> buscarPorId(Integer id) {
        return seccionRepository.findById(id);
    }

    public List<Seccion> obtenerPorAsignatura(Integer idAsignatura) {
        return seccionRepository.findByAsignaturaIdAsignatura(idAsignatura);
    }

    public List<Seccion> listarPorProfesor(Integer idProfesor) {
        return seccionRepository.findByProfesorIdProfesor(idProfesor);
    }

    public Seccion crear(Seccion seccion) {
        return seccionRepository.save(seccion);
    }

    public String actualizar(Integer id, Seccion datos) {
        return seccionRepository.findById(id).map(s -> {
            s.setCupos(datos.getCupos());
            s.setAsignatura(datos.getAsignatura());
            s.setProfesor(datos.getProfesor());
            s.setSala(datos.getSala());
            s.setSede(datos.getSede());
            s.setModalidad(datos.getModalidad());
            s.setJornada(datos.getJornada());
            seccionRepository.save(s);
            return "Sección actualizada";
        }).orElse("Sección no encontrada");
    }

    public String eliminar(Integer id) {
        if (!seccionRepository.existsById(id))
            return "Sección no encontrada";
        seccionRepository.deleteById(id);
        return "Sección eliminada";
    }

    public long contarAlumnosInscritos(Integer idSeccion) {
        return inscripcionRepository.countBySeccionIdSeccion(idSeccion);
    }

    @Transactional
    public Seccion crearConHorarios(SeccionRequestDTO dto) {
        Seccion seccion = new Seccion();
        seccion.setCupos(dto.getCupos());
        seccion.setAsignatura(asignaturaRepository.findById(dto.getIdAsignatura()).orElseThrow());
        seccion.setProfesor(profesorRepository.findById(dto.getIdProfesor()).orElseThrow());
        seccion.setSala(salaRepository.findById(dto.getIdSala()).orElseThrow());
        seccion.setSede(sedeRepository.findById(dto.getIdSede()).orElseThrow());
        seccion.setModalidad(modalidadRepository.findById(dto.getIdModalidad()).orElseThrow());
        seccion.setJornada(jornadaRepository.findById(dto.getIdJornada()).orElseThrow());
        Seccion saved = seccionRepository.save(seccion);

        for (Integer idHorario : dto.getIdHorarios()) {
            SeccionHorario sh = new SeccionHorario();
            sh.setSeccion(saved);
            sh.setHorario(horarioRepository.findById(idHorario).orElseThrow());
            seccionHorarioRepository.save(sh);
        }
        return saved;
    }

    @Transactional
    public String eliminarConHorarios(Integer id) {

        if (!seccionRepository.existsById(id))
            return "Sección no encontrada";

        inscripcionRepository.deleteBySeccionIdSeccion(id);

        seccionHorarioRepository.deleteBySeccionIdSeccion(id);

        seccionRepository.deleteById(id);

        return "Sección eliminada";
    }


    @Transactional
    public Seccion actualizarConHorarios(Integer id, SeccionRequestDTO dto) {
        Seccion seccion = seccionRepository.findById(id).orElseThrow();
        seccion.setCupos(dto.getCupos());
        seccion.setAsignatura(asignaturaRepository.findById(dto.getIdAsignatura()).orElseThrow());
        seccion.setProfesor(profesorRepository.findById(dto.getIdProfesor()).orElseThrow());
        seccion.setSala(salaRepository.findById(dto.getIdSala()).orElseThrow());
        seccion.setSede(sedeRepository.findById(dto.getIdSede()).orElseThrow());
        seccion.setModalidad(modalidadRepository.findById(dto.getIdModalidad()).orElseThrow());
        seccion.setJornada(jornadaRepository.findById(dto.getIdJornada()).orElseThrow());
        seccionRepository.save(seccion);

        List<SeccionHorario> horariosActuales = seccionHorarioRepository.findBySeccionIdSeccion(id);
        seccionHorarioRepository.deleteAllInBatch(horariosActuales);

        for (Integer idHorario : dto.getIdHorarios()) {
            SeccionHorario sh = new SeccionHorario();
            sh.setSeccion(seccion);
            sh.setHorario(horarioRepository.findById(idHorario).orElseThrow());
            seccionHorarioRepository.save(sh);
        }
        return seccion;
    }
}