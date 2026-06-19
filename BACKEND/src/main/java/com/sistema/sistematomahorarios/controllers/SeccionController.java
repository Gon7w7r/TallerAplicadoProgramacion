package com.sistema.sistematomahorarios.controllers;

import com.sistema.sistematomahorarios.dto.SeccionRequestDTO;
import com.sistema.sistematomahorarios.entities.Seccion;
import com.sistema.sistematomahorarios.enums.TipoUsuario;
import com.sistema.sistematomahorarios.security.RolRequerido;
import com.sistema.sistematomahorarios.services.SeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secciones")
public class SeccionController {

    @Autowired
    private SeccionService seccionService;

    @GetMapping
    @RolRequerido({ TipoUsuario.ALUMNO, TipoUsuario.PROFESOR, TipoUsuario.ADMINISTRATIVO })
    public List<Seccion> listarTodas() {
        return seccionService.listarTodas();
    }

    @GetMapping("/{id}")
    @RolRequerido({ TipoUsuario.ALUMNO, TipoUsuario.PROFESOR, TipoUsuario.ADMINISTRATIVO })
    public Object buscarPorId(@PathVariable Integer id) {
        return seccionService.buscarPorId(id).<Object>map(s -> s).orElse("Sección no encontrada");
    }

    @GetMapping("/asignatura/{idAsignatura}")
    @RolRequerido({ TipoUsuario.ALUMNO, TipoUsuario.PROFESOR, TipoUsuario.ADMINISTRATIVO })
    public List<Seccion> obtenerPorAsignatura(@PathVariable Integer idAsignatura) {
        return seccionService.obtenerPorAsignatura(idAsignatura);
    }

    @GetMapping("/profesor/{idProfesor}")
    @RolRequerido({ TipoUsuario.PROFESOR, TipoUsuario.ADMINISTRATIVO })
    public List<Seccion> listarPorProfesor(@PathVariable Integer idProfesor) {
        return seccionService.listarPorProfesor(idProfesor);
    }

    @PostMapping
    @RolRequerido({ TipoUsuario.ADMINISTRATIVO })
    public Seccion crear(@RequestBody Seccion seccion) {
        return seccionService.crear(seccion);
    }

    @PostMapping("/con-horarios")
    @RolRequerido({ TipoUsuario.ADMINISTRATIVO })
    public Seccion crearConHorarios(@RequestBody SeccionRequestDTO request) {
        return seccionService.crearConHorarios(request);
    }

    @PutMapping("/{id}")
    @RolRequerido({ TipoUsuario.ADMINISTRATIVO })
    public String actualizar(@PathVariable Integer id, @RequestBody Seccion datos) {
        return seccionService.actualizar(id, datos);
    }

    @DeleteMapping("/{id}")
    @RolRequerido({ TipoUsuario.ADMINISTRATIVO })
    public String eliminar(@PathVariable Integer id) {
        return seccionService.eliminar(id);
    }

    @DeleteMapping("/{id}/con-horarios")
    @RolRequerido({ TipoUsuario.ADMINISTRATIVO })
    public String eliminarConHorarios(@PathVariable Integer id) {
        return seccionService.eliminarConHorarios(id);
    }

    @GetMapping("/{id}/alumnos-inscritos")
    @RolRequerido({ TipoUsuario.ADMINISTRATIVO })
    public long contarAlumnosInscritos(@PathVariable Integer id) {
        return seccionService.contarAlumnosInscritos(id);
    }

    @PutMapping("/{id}/con-horarios")
    @RolRequerido({ TipoUsuario.ADMINISTRATIVO })
    public Seccion actualizarConHorarios(@PathVariable Integer id, @RequestBody SeccionRequestDTO request) {
        return seccionService.actualizarConHorarios(id, request);
    }
}