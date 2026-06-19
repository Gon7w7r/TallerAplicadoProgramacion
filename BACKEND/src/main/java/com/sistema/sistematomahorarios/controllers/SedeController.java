package com.sistema.sistematomahorarios.controllers;

import com.sistema.sistematomahorarios.entities.Sede;
import com.sistema.sistematomahorarios.services.SedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sedes")
public class SedeController {

    @Autowired
    private SedeService sedeService;

    @GetMapping
    public List<Sede> listarTodas() {
        return sedeService.listarTodas();
    }

    @GetMapping("/{id}")
    public Object buscarPorId(@PathVariable Integer id) {
        return sedeService.buscarPorId(id)
                .<Object>map(s -> s)
                .orElse("Sede no encontrada");
    }

    @PostMapping
    public Sede crear(@RequestBody Sede sede) {
        return sedeService.crear(sede);
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable Integer id, @RequestBody Sede datos) {
        return sedeService.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        return sedeService.eliminar(id);
    }
}
