package com.sistema.sistematomahorarios.services;

import com.sistema.sistematomahorarios.entities.Jornada;
import com.sistema.sistematomahorarios.repositories.JornadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JornadaService {

    @Autowired
    private JornadaRepository jornadaRepository;

    public List<Jornada> listarTodas() {
        return jornadaRepository.findAll();
    }

    public Optional<Jornada> buscarPorId(Integer id) {
        return jornadaRepository.findById(id);
    }

    public Jornada crear(Jornada jornada) {
        return jornadaRepository.save(jornada);
    }

    public String actualizar(Integer id, Jornada datos) {
        return jornadaRepository.findById(id).map(j -> {
            j.setNombre(datos.getNombre());
            jornadaRepository.save(j);
            return "Jornada actualizada";
        }).orElse("Jornada no encontrada");
    }

    public String eliminar(Integer id) {
        if (!jornadaRepository.existsById(id)) {
            return "Jornada no encontrada";
        }
        jornadaRepository.deleteById(id);
        return "Jornada eliminada";
    }
}
