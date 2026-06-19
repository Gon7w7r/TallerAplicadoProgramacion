package com.sistema.sistematomahorarios.services;

import com.sistema.sistematomahorarios.entities.Sede;
import com.sistema.sistematomahorarios.repositories.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SedeService {

    @Autowired
    private SedeRepository sedeRepository;

    public List<Sede> listarTodas() {
        return sedeRepository.findAll();
    }

    public Optional<Sede> buscarPorId(Integer id) {
        return sedeRepository.findById(id);
    }

    public Sede crear(Sede sede) {
        return sedeRepository.save(sede);
    }

    public String actualizar(Integer id, Sede datos) {
        return sedeRepository.findById(id).map(s -> {
            s.setNombre(datos.getNombre());
            sedeRepository.save(s);
            return "Sede actualizada";
        }).orElse("Sede no encontrada");
    }

    public String eliminar(Integer id) {
        if (!sedeRepository.existsById(id)) {
            return "Sede no encontrada";
        }
        sedeRepository.deleteById(id);
        return "Sede eliminada";
    }
}
