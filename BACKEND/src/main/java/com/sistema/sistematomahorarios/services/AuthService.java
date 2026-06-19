package com.sistema.sistematomahorarios.services;

import com.sistema.sistematomahorarios.entities.Usuario;
import com.sistema.sistematomahorarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario login(String rut, String password) {

        Usuario usuario = usuarioRepository.findByRut(rut);

        if (usuario == null) return null;

        if (!passwordEncoder.matches(password, usuario.getPasswordHash())) return null;

        return usuario;
    }
}