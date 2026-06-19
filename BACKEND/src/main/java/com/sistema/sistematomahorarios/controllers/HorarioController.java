package com.sistema.sistematomahorarios.controllers;

import com.sistema.sistematomahorarios.entities.Horario;
import com.sistema.sistematomahorarios.repositories.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horarios")
public class HorarioController {

    @Autowired
    private HorarioRepository horarioRepository;

    @GetMapping
    public List<Horario> listarTodos() {
        return horarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Object buscarPorId(@PathVariable Integer id) {
        return horarioRepository.findById(id)
                .<Object>map(h -> h)
                .orElse("Horario no encontrado");
    }

    @PostMapping
    public Horario crear(@RequestBody Horario horario) {
        return horarioRepository.save(horario);
    }

    @PutMapping("/{id}")
    public Object actualizar(@PathVariable Integer id, @RequestBody Horario datos) {
        return horarioRepository.findById(id)
                .<Object>map(h -> {
                    h.setDiaSemana(datos.getDiaSemana());
                    h.setHoraInicio(datos.getHoraInicio());
                    h.setHoraFin(datos.getHoraFin());
                    return horarioRepository.save(h);
                }).orElse("Horario no encontrado");
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        if (!horarioRepository.existsById(id))
            return "Horario no encontrado";
        horarioRepository.deleteById(id);
        return "Horario eliminado";
    }
}