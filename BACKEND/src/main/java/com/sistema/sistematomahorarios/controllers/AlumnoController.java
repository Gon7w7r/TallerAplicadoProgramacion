package com.sistema.sistematomahorarios.controllers;


import com.sistema.sistematomahorarios.dto.AsignaturaDisponibleDTO;
import com.sistema.sistematomahorarios.enums.TipoUsuario;
import com.sistema.sistematomahorarios.security.RolRequerido;
import com.sistema.sistematomahorarios.services.AlumnoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/alumnos")
public class AlumnoController {


    @Autowired
    private AlumnoService alumnoService;

    @GetMapping("/{idAlumno}/asignaturas-disponibles")
    @RolRequerido({ TipoUsuario.ALUMNO, TipoUsuario.ADMINISTRATIVO })
    public List<AsignaturaDisponibleDTO> obtenerAsignaturasDisponibles(
            @PathVariable Integer idAlumno) {
        return alumnoService.obtenerAsignaturasDisponibles(idAlumno);
    }

}