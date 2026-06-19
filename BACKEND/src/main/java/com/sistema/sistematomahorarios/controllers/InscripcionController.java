package com.sistema.sistematomahorarios.controllers;

import com.sistema.sistematomahorarios.dto.InscripcionMultipleRequestDTO;
import com.sistema.sistematomahorarios.dto.InscripcionRequestDTO;
import com.sistema.sistematomahorarios.dto.InscripcionResponseDTO;
import com.sistema.sistematomahorarios.enums.TipoUsuario;
import com.sistema.sistematomahorarios.security.RolRequerido;
import com.sistema.sistematomahorarios.services.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @PostMapping("/inscribir")
    @RolRequerido({ TipoUsuario.ALUMNO, TipoUsuario.ADMINISTRATIVO })
    public String inscribir(@RequestBody InscripcionRequestDTO request) {
        return inscripcionService.inscribir(
                request.getIdAlumno(), request.getIdSeccion(), request.getIdPeriodo());
    }

    @GetMapping("/alumno/{idAlumno}")
    @RolRequerido({ TipoUsuario.ALUMNO, TipoUsuario.ADMINISTRATIVO })
    public List<InscripcionResponseDTO> obtenerInscripcionesAlumno(
            @PathVariable Integer idAlumno) {
        return inscripcionService.obtenerPorAlumno(idAlumno);
    }

    @PostMapping("/inscribir-multiple")
    @RolRequerido({ TipoUsuario.ALUMNO, TipoUsuario.ADMINISTRATIVO })
    public List<String> inscribirMultiple(@RequestBody InscripcionMultipleRequestDTO request) {
        return inscripcionService.inscribirMultiple(
                request.getIdAlumno(), request.getSecciones(), request.getIdPeriodo());
    }

    @PutMapping("/actualizar-horario")
    @RolRequerido({ TipoUsuario.ALUMNO, TipoUsuario.ADMINISTRATIVO })
    public List<String> actualizarHorario(@RequestBody InscripcionMultipleRequestDTO request) {
        return inscripcionService.actualizarHorario(
                request.getIdAlumno(),
                request.getSecciones(),
                request.getIdPeriodo());
    }
}