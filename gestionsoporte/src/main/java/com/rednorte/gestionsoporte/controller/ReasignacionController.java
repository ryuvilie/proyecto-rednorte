package com.rednorte.gestionsoporte.controller;

import com.rednorte.gestionsoporte.model.Reasignacion;
import com.rednorte.gestionsoporte.service.ReasignacionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reasignaciones")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ReasignacionController {

    private final ReasignacionService reasignacionService;

    public ReasignacionController(ReasignacionService reasignacionService) {
        this.reasignacionService = reasignacionService;
    }

    @GetMapping
    public List<Reasignacion> listar() {
        return reasignacionService.listar();
    }

    @PostMapping
    public Reasignacion guardar(@Valid @RequestBody Reasignacion reasignacion) {
        return reasignacionService.guardar(reasignacion);
    }
}