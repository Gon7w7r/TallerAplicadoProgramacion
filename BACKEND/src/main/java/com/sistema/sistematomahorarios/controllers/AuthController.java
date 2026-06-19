package com.sistema.sistematomahorarios.controllers;

import com.sistema.sistematomahorarios.dto.LoginRequest;
import com.sistema.sistematomahorarios.dto.LoginResponse;
import com.sistema.sistematomahorarios.entities.*;
import com.sistema.sistematomahorarios.repositories.*;
import com.sistema.sistematomahorarios.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

        @Autowired
        private AuthService authService;
        @Autowired
        private AlumnoRepository alumnoRepository;
        @Autowired
        private ProfesorRepository profesorRepository;
        @Autowired
        private AdministrativoRepository administrativoRepository;

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest request) {

                Usuario usuario = authService.login(request.getRut(), request.getPassword());

                if (usuario == null) {
                        return ResponseEntity.status(401).body("RUT o contraseña incorrectos");
                }

                Integer idEntidad = resolverIdEntidad(usuario);

                LoginResponse response = new LoginResponse(
                                idEntidad,
                                usuario.getRut(),
                                usuario.getNombre(),
                                usuario.getTipoUsuario());

                return ResponseEntity.ok(response);
        }

        // ── Resuelve el id según el tipo ───────────────────────────────────────
        private Integer resolverIdEntidad(Usuario usuario) {
                return switch (usuario.getTipoUsuario()) {
                        case "ALUMNO" -> {
                                Alumno a = alumnoRepository.findByUsuarioRut(usuario.getRut());
                                yield a != null ? a.getIdAlumno() : null;
                        }
                        case "PROFESOR" -> {
                                Profesor p = profesorRepository.findByUsuarioRut(usuario.getRut());
                                yield p != null ? p.getIdProfesor() : null;
                        }
                        case "ADMINISTRATIVO" -> {
                                Administrativo adm = administrativoRepository.findByUsuarioRut(usuario.getRut());
                                yield adm != null ? adm.getIdAdministrativo() : null;
                        }
                        default -> null; // SUPERADMIN no tiene entidad propia
                };
        }
}