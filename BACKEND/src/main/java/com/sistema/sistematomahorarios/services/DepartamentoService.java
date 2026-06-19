package com.sistema.sistematomahorarios.services;

import com.sistema.sistematomahorarios.entities.Departamento;
import com.sistema.sistematomahorarios.repositories.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<Departamento> listarTodos() {
        return departamentoRepository.findAll();
    }

    public Optional<Departamento> buscarPorId(Integer id) {
        return departamentoRepository.findById(id);
    }

    public Departamento crear(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    public String actualizar(Integer id, Departamento datos) {
        return departamentoRepository.findById(id).map(d -> {
            d.setNombre(datos.getNombre());
            departamentoRepository.save(d);
            return "Departamento actualizado";
        }).orElse("Departamento no encontrado");
    }

    public String eliminar(Integer id) {
        if (!departamentoRepository.existsById(id)) {
            return "Departamento no encontrado";
        }
        departamentoRepository.deleteById(id);
        return "Departamento eliminado";
    }
}
