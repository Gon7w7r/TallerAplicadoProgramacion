package com.sistema.sistematomahorarios.services;

import com.sistema.sistematomahorarios.dto.DashboardDTO;
import com.sistema.sistematomahorarios.dto.DemandaDTO;
import com.sistema.sistematomahorarios.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private InscripcionRepository inscripcionRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;
    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired
    private AsignaturaRepository asignaturaRepository;
    @Autowired
    private SeccionRepository seccionRepository;
    @Autowired
    private AlumnoCarreraRepository alumnoCarreraRepository;

    public DashboardDTO obtenerResumen() {
        return new DashboardDTO(
                inscripcionRepository.countAlumnosConInscripcion(),
                departamentoRepository.count(),
                carreraRepository.count(),
                asignaturaRepository.count(),
                seccionRepository.count(),
                alumnoCarreraRepository.countAlumnosPorCarrera(),
                seccionRepository.countSeccionesPorAsignatura());
    }

    public List<DemandaDTO> obtenerDemanda() {
        return seccionRepository.getDemandaPorAsignatura();
    }
}