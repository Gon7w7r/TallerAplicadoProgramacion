package com.sistema.sistematomahorarios.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sistema.sistematomahorarios.entities.Asignatura;
import com.sistema.sistematomahorarios.services.AsignaturaService;

import java.util.List;

@RestController
@RequestMapping("/asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    @GetMapping
    public List<Asignatura> listarTodas() {
        return asignaturaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Object buscarPorId(@PathVariable Integer id) {
        return asignaturaService.buscarPorId(id)
                .<Object>map(a -> a)
                .orElse("Asignatura no encontrada");
    }

    @GetMapping("/departamento/{idDepartamento}")
    public List<Asignatura> listarPorDepartamento(@PathVariable Integer idDepartamento) {
        return asignaturaService.listarPorDepartamento(idDepartamento);
    }

    @PostMapping
    public Asignatura crear(@RequestBody Asignatura asignatura) {
        return asignaturaService.crear(asignatura);
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable Integer id, @RequestBody Asignatura datos) {
        return asignaturaService.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        return asignaturaService.eliminar(id);
    }
}
