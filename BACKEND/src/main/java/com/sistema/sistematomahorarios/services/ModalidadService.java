package com.sistema.sistematomahorarios.services;

import com.sistema.sistematomahorarios.entities.Modalidad;
import com.sistema.sistematomahorarios.repositories.ModalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModalidadService {

    @Autowired
    private ModalidadRepository modalidadRepository;

    public List<Modalidad> listarTodas() {
        return modalidadRepository.findAll();
    }

    public Optional<Modalidad> buscarPorId(Integer id) {
        return modalidadRepository.findById(id);
    }

    public Modalidad crear(Modalidad modalidad) {
        return modalidadRepository.save(modalidad);
    }

    public String actualizar(Integer id, Modalidad datos) {
        return modalidadRepository.findById(id).map(m -> {
            m.setNombre(datos.getNombre());
            modalidadRepository.save(m);
            return "Modalidad actualizada";
        }).orElse("Modalidad no encontrada");
    }

    public String eliminar(Integer id) {
        if (!modalidadRepository.existsById(id)) {
            return "Modalidad no encontrada";
        }
        modalidadRepository.deleteById(id);
        return "Modalidad eliminada";
    }
}
