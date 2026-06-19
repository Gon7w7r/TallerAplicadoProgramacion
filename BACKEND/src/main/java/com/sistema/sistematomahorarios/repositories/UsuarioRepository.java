package com.sistema.sistematomahorarios.repositories;

import com.sistema.sistematomahorarios.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findByRut(String rut);

}
