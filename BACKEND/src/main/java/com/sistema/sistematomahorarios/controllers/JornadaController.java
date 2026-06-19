package com.sistema.sistematomahorarios.controllers;

import com.sistema.sistematomahorarios.entities.Jornada;
import com.sistema.sistematomahorarios.services.JornadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jornadas")
public class JornadaController {

    @Autowired
    private JornadaService jornadaService;

    @GetMapping
    public List<Jornada> listarTodas() {
        return jornadaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Object buscarPorId(@PathVariable Integer id) {
        return jornadaService.buscarPorId(id)
                .<Object>map(j -> j)
                .orElse("Jornada no encontrada");
    }

    @PostMapping
    public Jornada crear(@RequestBody Jornada jornada) {
        return jornadaService.crear(jornada);
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable Integer id, @RequestBody Jornada datos) {
        return jornadaService.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        return jornadaService.eliminar(id);
    }
}
