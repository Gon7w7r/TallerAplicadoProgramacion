package com.sistema.sistematomahorarios.security;

import com.sistema.sistematomahorarios.enums.TipoUsuario;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RolRequerido {
    TipoUsuario[] value();
}