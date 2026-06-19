package com.sistema.sistematomahorarios.controllers;

import com.sistema.sistematomahorarios.entities.Profesor;
import com.sistema.sistematomahorarios.enums.TipoUsuario;
import com.sistema.sistematomahorarios.repositories.ProfesorRepository;
import com.sistema.sistematomahorarios.security.RolRequerido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorRepository profesorRepository;

    @GetMapping
    @RolRequerido({ TipoUsuario.ADMINISTRATIVO, TipoUsuario.ALUMNO })
    public List<Profesor> listarTodos() {
        return profesorRepository.findAll();
    }

    @PutMapping("/{idProfesor}/max-secciones")
    @RolRequerido({ TipoUsuario.PROFESOR })
    public String actualizarMaxSecciones(
            @PathVariable Integer idProfesor,
            @RequestBody java.util.Map<String, Integer> body) {
        return profesorRepository.findById(idProfesor).map(p -> {
            p.setMaxSecciones(body.get("maxSecciones"));
            profesorRepository.save(p);
            return "Max secciones actualizado";
        }).orElse("Profesor no encontrado");
    }
}