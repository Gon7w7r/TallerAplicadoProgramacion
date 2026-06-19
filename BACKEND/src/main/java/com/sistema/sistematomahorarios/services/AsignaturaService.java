package com.sistema.sistematomahorarios.services;

import com.sistema.sistematomahorarios.entities.Asignatura;
import com.sistema.sistematomahorarios.repositories.AsignaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignaturaService {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    public List<Asignatura> listarTodas() {
        return asignaturaRepository.findAll();
    }

    public Optional<Asignatura> buscarPorId(Integer id) {
        return asignaturaRepository.findById(id);
    }

    public List<Asignatura> listarPorDepartamento(Integer idDepartamento) {
        return asignaturaRepository.findByDepartamentoIdDepartamento(idDepartamento);
    }

    public Asignatura crear(Asignatura asignatura) {
        return asignaturaRepository.save(asignatura);
    }

    public String actualizar(Integer id, Asignatura datos) {
        return asignaturaRepository.findById(id).map(a -> {
            a.setNombre(datos.getNombre());
            a.setNivel(datos.getNivel());
            a.setDepartamento(datos.getDepartamento());
            asignaturaRepository.save(a);
            return "Asignatura actualizada";
        }).orElse("Asignatura no encontrada");
    }

    public String eliminar(Integer id) {
        if (!asignaturaRepository.existsById(id)) {
            return "Asignatura no encontrada";
        }
        asignaturaRepository.deleteById(id);
        return "Asignatura eliminada";
    }
}
