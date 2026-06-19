package com.sistema.sistematomahorarios.controllers;

import com.sistema.sistematomahorarios.dto.DashboardDTO;
import com.sistema.sistematomahorarios.enums.TipoUsuario;
import com.sistema.sistematomahorarios.security.RolRequerido;
import com.sistema.sistematomahorarios.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sistema.sistematomahorarios.dto.DemandaDTO;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/resumen")
    @RolRequerido({ TipoUsuario.ADMINISTRATIVO })
    public DashboardDTO obtenerResumen() {
        return dashboardService.obtenerResumen();
    }

    @GetMapping("/demanda")
    @RolRequerido({ TipoUsuario.ADMINISTRATIVO })
    public List<DemandaDTO> obtenerDemanda() {
        return dashboardService.obtenerDemanda();
    }
}