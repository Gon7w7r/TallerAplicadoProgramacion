package com.sistema.sistematomahorarios.config;

import com.sistema.sistematomahorarios.entities.Usuario;
import com.sistema.sistematomahorarios.repositories.UsuarioRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;
@Component
public class PasswordMigration implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public PasswordMigration(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<Usuario> usuarios = usuarioRepository.findAll();

        for (Usuario u : usuarios) {
            String pass = u.getPasswordHash();

            // Solo hashea si NO empieza por $2a$ (ya hasheado)
            if (pass != null && !pass.startsWith("$2a$")) {
                u.setPasswordHash(encoder.encode(pass));
                usuarioRepository.save(u);
                System.out.println("Contraseña migrada: " + u.getRut());
            }
        }

        System.out.println("Migración de contraseñas completada.");
    }
}