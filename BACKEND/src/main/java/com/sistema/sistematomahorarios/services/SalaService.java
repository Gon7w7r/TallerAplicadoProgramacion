package com.sistema.sistematomahorarios.services;

import com.sistema.sistematomahorarios.dto.DisponibilidadSalaDTO;
import com.sistema.sistematomahorarios.entities.Sala;
import com.sistema.sistematomahorarios.entities.SeccionHorario;
import com.sistema.sistematomahorarios.repositories.SalaRepository;
import com.sistema.sistematomahorarios.repositories.SeccionHorarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SeccionHorarioRepository seccionHorarioRepository;

    public List<Sala> listarTodas() {
        return salaRepository.findAll();
    }

    public Optional<Sala> buscarPorId(Integer id) {
        return salaRepository.findById(id);
    }

    public List<Sala> listarPorSede(Integer idSede) {
        return salaRepository.findBySedeIdSede(idSede);
    }

    public Sala crear(Sala sala) {
        return salaRepository.save(sala);
    }

    public String actualizar(Integer id, Sala datos) {
        return salaRepository.findById(id).map(s -> {
            s.setNombre(datos.getNombre());
            s.setTipo(datos.getTipo());
            s.setSede(datos.getSede());
            salaRepository.save(s);
            return "Sala actualizada";
        }).orElse("Sala no encontrada");
    }

    public String eliminar(Integer id) {
        if (!salaRepository.existsById(id)) {
            return "Sala no encontrada";
        }
        salaRepository.deleteById(id);
        return "Sala eliminada";
    }

    public List<DisponibilidadSalaDTO> getDisponibilidadPorDia(String dia) {
        // Todas las salas
        List<Sala> salas = salaRepository.findAll();

        // Todos los horarios del día
        List<SeccionHorario> seccionesDelDia = seccionHorarioRepository.findAll()
                .stream()
                .filter(sh -> normalizar(sh.getHorario().getDiaSemana()).equals(normalizar(dia)))
                .toList();

        List<DisponibilidadSalaDTO> resultado = new ArrayList<>();

        for (Sala sala : salas) {
            for (SeccionHorario sh : seccionesDelDia) {
                if (sh.getSeccion().getSala().getIdSala().equals(sala.getIdSala())) {
                    resultado.add(new DisponibilidadSalaDTO(
                            sala.getNombre(),
                            sh.getHorario().getHoraInicio().toString().substring(0, 5),
                            sh.getHorario().getHoraFin().toString().substring(0, 5),
                            true,
                            sh.getSeccion().getAsignatura().getNombre()));
                }
            }
        }

        return resultado;
    }

    private String normalizar(String s) {
        return s.toUpperCase()
                .replace("É", "E").replace("Á", "A").replace("Ó", "O")
                .replace("Í", "I").replace("Ú", "U")
                .replace("é", "e").replace("á", "a").replace("ó", "o")
                .replace("í", "i").replace("ú", "u");
    }
}