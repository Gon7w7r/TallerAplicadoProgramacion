package com.sistema.sistematomahorarios.controllers;

import com.sistema.sistematomahorarios.entities.Asignatura;
import com.sistema.sistematomahorarios.entities.Carrera;
import com.sistema.sistematomahorarios.enums.TipoUsuario;
import com.sistema.sistematomahorarios.security.RolRequerido;
import com.sistema.sistematomahorarios.services.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    @GetMapping
    public List<Carrera> listarTodas() {
        return carreraService.listarTodas();
    }

    @GetMapping("/{id}")
    public Object buscarPorId(@PathVariable Integer id) {
        return carreraService.buscarPorId(id)
                .<Object>map(c -> c)
                .orElse("Carrera no encontrada");
    }

    @GetMapping("/tipo/{tipo}")
    public List<Carrera> listarPorTipo(@PathVariable String tipo) {
        return carreraService.listarPorTipo(tipo);
    }

    @PostMapping
    public Carrera crear(@RequestBody Carrera carrera) {
        return carreraService.crear(carrera);
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable Integer id, @RequestBody Carrera datos) {
        return carreraService.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        return carreraService.eliminar(id);
    }

    @GetMapping("/{id}/asignaturas")
    @RolRequerido({ TipoUsuario.ADMINISTRATIVO })
    public List<Asignatura> obtenerAsignaturas(@PathVariable Integer id) {
        return carreraService.obtenerAsignaturas(id);
    }

    @PostMapping("/{id}/asignaturas/{idAsignatura}")
    @RolRequerido({ TipoUsuario.ADMINISTRATIVO })
    public String agregarAsignatura(
            @PathVariable Integer id,
            @PathVariable Integer idAsignatura) {
        return carreraService.agregarAsignatura(id, idAsignatura);
    }

    @DeleteMapping("/{id}/asignaturas/{idAsignatura}")
    @RolRequerido({ TipoUsuario.ADMINISTRATIVO })
    public String quitarAsignatura(
            @PathVariable Integer id,
            @PathVariable Integer idAsignatura) {
        return carreraService.quitarAsignatura(id, idAsignatura);
    }
}