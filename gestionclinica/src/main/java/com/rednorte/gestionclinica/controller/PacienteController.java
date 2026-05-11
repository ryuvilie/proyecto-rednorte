package com.rednorte.gestionclinica.controller;

import com.rednorte.gestionclinica.model.Paciente;
import com.rednorte.gestionclinica.service.PacienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public List<Paciente> listar() {
        return pacienteService.listar();
    }

    @PostMapping
    public Paciente guardar(@RequestBody Paciente paciente) {
        return pacienteService.guardar(paciente);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        pacienteService.eliminar(id);
    }

    @DeleteMapping("/{id}/dependencias")
    public void limpiarDependencias(@PathVariable Long id) {
        pacienteService.limpiarDependencias(id);
    }
}