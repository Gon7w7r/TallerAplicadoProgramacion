package com.sistema.sistematomahorarios.services;

import com.sistema.sistematomahorarios.entities.Periodo;
import com.sistema.sistematomahorarios.repositories.PeriodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeriodoService {

    @Autowired
    private PeriodoRepository periodoRepository;

    public List<Periodo> listarTodos() {
        return periodoRepository.findAll();
    }

    public Optional<Periodo> buscarPorId(Integer id) {
        return periodoRepository.findById(id);
    }

    public Periodo crear(Periodo periodo) {
        return periodoRepository.save(periodo);
    }

    public String actualizar(Integer id, Periodo datos) {
        return periodoRepository.findById(id).map(p -> {
            p.setNombre(datos.getNombre());
            periodoRepository.save(p);
            return "Periodo actualizado";
        }).orElse("Periodo no encontrado");
    }

    public String eliminar(Integer id) {
        if (!periodoRepository.existsById(id)) {
            return "Periodo no encontrado";
        }
        periodoRepository.deleteById(id);
        return "Periodo eliminado";
    }
}
