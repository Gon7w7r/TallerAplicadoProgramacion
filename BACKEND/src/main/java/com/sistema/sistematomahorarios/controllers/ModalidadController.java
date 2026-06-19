package com.sistema.sistematomahorarios.controllers;

import com.sistema.sistematomahorarios.entities.Modalidad;
import com.sistema.sistematomahorarios.services.ModalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modalidades")
public class ModalidadController {

    @Autowired
    private ModalidadService modalidadService;

    @GetMapping
    public List<Modalidad> listarTodas() {
        return modalidadService.listarTodas();
    }

    @GetMapping("/{id}")
    public Object buscarPorId(@PathVariable Integer id) {
        return modalidadService.buscarPorId(id)
                .<Object>map(m -> m)
                .orElse("Modalidad no encontrada");
    }

    @PostMapping
    public Modalidad crear(@RequestBody Modalidad modalidad) {
        return modalidadService.crear(modalidad);
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable Integer id, @RequestBody Modalidad datos) {
        return modalidadService.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        return modalidadService.eliminar(id);
    }
}
