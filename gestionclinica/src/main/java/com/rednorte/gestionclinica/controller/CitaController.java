package com.rednorte.gestionclinica.controller;

import com.rednorte.gestionclinica.model.Cita;
import com.rednorte.gestionclinica.service.CitaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "http://32.197.111.18:5173"
})
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public List<Cita> listar() {
        return citaService.listar();
    }

    @PostMapping
    public Cita crearDisponibilidad(@RequestBody Cita cita) {
        return citaService.crearDisponibilidad(cita);
    }

    @GetMapping("/disponibles")
    public List<Cita> listarDisponibles() {
        return citaService.listarDisponibles();
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Cita> listarPorDoctor(@PathVariable Long doctorId) {
        return citaService.listarPorDoctor(doctorId);
    }

    @PutMapping("/{citaId}/reservar/{listaEsperaId}")
    public Cita reservarCita(
            @PathVariable Long citaId,
            @PathVariable Long listaEsperaId
    ) {
        return citaService.reservarCita(citaId, listaEsperaId);
    }

    @PutMapping("/{citaId}/reservar-paciente/{pacienteId}")
    public Cita reservarCitaPaciente(
            @PathVariable Long citaId,
            @PathVariable Long pacienteId
    ) {
        return citaService.reservarCitaPaciente(citaId, pacienteId);
    }

    @DeleteMapping("/{id}")
        public void eliminarCita(@PathVariable Long id) {
            citaService.eliminarCita(id);
    }
}