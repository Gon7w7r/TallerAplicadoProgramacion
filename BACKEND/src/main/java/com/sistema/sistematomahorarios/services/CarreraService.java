package com.sistema.sistematomahorarios.services;

import com.sistema.sistematomahorarios.entities.Asignatura;
import com.sistema.sistematomahorarios.entities.AsignaturaCarrera;
import com.sistema.sistematomahorarios.entities.Carrera;
import com.sistema.sistematomahorarios.repositories.AsignaturaCarerraRepository;
import com.sistema.sistematomahorarios.repositories.AsignaturaRepository;
import com.sistema.sistematomahorarios.repositories.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarreraService {

    @Autowired private CarreraRepository          carreraRepository;
    @Autowired private AsignaturaCarerraRepository asignaturaCarreraRepository;
    @Autowired private AsignaturaRepository        asignaturaRepository;

    public List<Carrera> listarTodas() {
        return carreraRepository.findAll();
    }

    public Optional<Carrera> buscarPorId(Integer id) {
        return carreraRepository.findById(id);
    }

    public List<Carrera> listarPorTipo(String tipo) {
        return carreraRepository.findByTipo(tipo);
    }

    public Carrera crear(Carrera carrera) {
        return carreraRepository.save(carrera);
    }

    public String actualizar(Integer id, Carrera datos) {
        return carreraRepository.findById(id).map(c -> {
            c.setNombre(datos.getNombre());
            c.setTipo(datos.getTipo());
            carreraRepository.save(c);
            return "Carrera actualizada";
        }).orElse("Carrera no encontrada");
    }

    public String eliminar(Integer id) {
        if (!carreraRepository.existsById(id)) return "Carrera no encontrada";
        carreraRepository.deleteById(id);
        return "Carrera eliminada";
    }

    // ── Asignaturas de una carrera ────────────────────────────────────────
    public List<Asignatura> obtenerAsignaturas(Integer idCarrera) {
        return asignaturaCarreraRepository.findByCarreraIdCarrera(idCarrera)
                .stream()
                .map(AsignaturaCarrera::getAsignatura)
                .toList();
    }

    public String agregarAsignatura(Integer idCarrera, Integer idAsignatura) {
        Carrera    carrera    = carreraRepository.findById(idCarrera).orElseThrow();
        Asignatura asignatura = asignaturaRepository.findById(idAsignatura).orElseThrow();

        AsignaturaCarrera ac = new AsignaturaCarrera();
        ac.setCarrera(carrera);
        ac.setAsignatura(asignatura);
        asignaturaCarreraRepository.save(ac);
        return "Asignatura agregada";
    }

    public String quitarAsignatura(Integer idCarrera, Integer idAsignatura) {
        asignaturaCarreraRepository
            .deleteByCarreraIdCarreraAndAsignaturaIdAsignatura(idCarrera, idAsignatura);
        return "Asignatura eliminada";
    }
}