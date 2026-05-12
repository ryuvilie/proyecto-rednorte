package com.rednorte.gestionsoporte.controller;

import com.rednorte.gestionsoporte.model.Notificacion;
import com.rednorte.gestionsoporte.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "http://54.83.181.63:5173"
})
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    public List<Notificacion> listar() {
        return notificacionService.listar();
    }

    @PostMapping
    public Notificacion guardar(@Valid @RequestBody Notificacion notificacion) {
        return notificacionService.guardar(notificacion);
    }
}