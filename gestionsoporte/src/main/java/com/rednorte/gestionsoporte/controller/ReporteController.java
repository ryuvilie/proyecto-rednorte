package com.rednorte.gestionsoporte.controller;

import com.rednorte.gestionsoporte.model.Reporte;
import com.rednorte.gestionsoporte.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportes")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "http://32.197.111.18:5173"
})
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public List<Reporte> listar() {
        return reporteService.listar();
    }

    @PostMapping
    public Reporte guardar(@Valid @RequestBody Reporte reporte) {
        return reporteService.guardar(reporte);
    }
}