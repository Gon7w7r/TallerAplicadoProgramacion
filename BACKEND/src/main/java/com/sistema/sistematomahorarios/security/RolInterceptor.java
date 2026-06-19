package com.sistema.sistematomahorarios.security;

import com.sistema.sistematomahorarios.enums.TipoUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RolInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        // Solo aplica a métodos de controllers
        if (!(handler instanceof HandlerMethod)) return true;

        RolRequerido anotacion = ((HandlerMethod) handler)
                .getMethodAnnotation(RolRequerido.class);

        // Si el método no tiene la anotación, pasa libre
        if (anotacion == null) return true;

        String rolHeader = request.getHeader("X-Tipo-Usuario");

        if (rolHeader == null || rolHeader.isBlank()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Autenticación requerida");
            return false;
        }

        TipoUsuario rolSolicitante;
        try {
            rolSolicitante = TipoUsuario.valueOf(rolHeader.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Rol desconocido");
            return false;
        }

        // SUPERADMIN pasa siempre
        if (rolSolicitante == TipoUsuario.SUPERADMIN) return true;

        for (TipoUsuario permitido : anotacion.value()) {
            if (permitido == rolSolicitante) return true;
        }

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Sin permisos para este recurso");
        return false;
    }
}