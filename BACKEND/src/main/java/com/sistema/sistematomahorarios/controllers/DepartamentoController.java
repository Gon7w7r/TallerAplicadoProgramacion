package com.sistema.sistematomahorarios.controllers;

import com.sistema.sistematomahorarios.entities.Departamento;
import com.sistema.sistematomahorarios.services.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public List<Departamento> listarTodos() {
        return departamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Object buscarPorId(@PathVariable Integer id) {
        return departamentoService.buscarPorId(id)
                .<Object>map(d -> d)
                .orElse("Departamento no encontrado");
    }

    @PostMapping
    public Departamento crear(@RequestBody Departamento departamento) {
        return departamentoService.crear(departamento);
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable Integer id, @RequestBody Departamento datos) {
        return departamentoService.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        return departamentoService.eliminar(id);
    }
}
