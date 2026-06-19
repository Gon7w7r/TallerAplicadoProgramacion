package com.sistema.sistematomahorarios.controllers;

import com.sistema.sistematomahorarios.entities.Periodo;
import com.sistema.sistematomahorarios.services.PeriodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/periodos")
public class PeriodoController {

    @Autowired
    private PeriodoService periodoService;

    @GetMapping
    public List<Periodo> listarTodos() {
        return periodoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Object buscarPorId(@PathVariable Integer id) {
        return periodoService.buscarPorId(id)
                .<Object>map(p -> p)
                .orElse("Periodo no encontrado");
    }

    @PostMapping
    public Periodo crear(@RequestBody Periodo periodo) {
        return periodoService.crear(periodo);
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable Integer id, @RequestBody Periodo datos) {
        return periodoService.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        return periodoService.eliminar(id);
    }
}
