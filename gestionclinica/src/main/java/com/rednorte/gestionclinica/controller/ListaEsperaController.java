package com.rednorte.gestionclinica.controller;

import com.rednorte.gestionclinica.model.ListaEspera;
import com.rednorte.gestionclinica.service.ListaEsperaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lista-espera")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ListaEsperaController {

    private final ListaEsperaService listaEsperaService;

    public ListaEsperaController(ListaEsperaService listaEsperaService) {
        this.listaEsperaService = listaEsperaService;
    }

    @GetMapping
    public List<ListaEspera> listar() {
        return listaEsperaService.listar();
    }

    @PostMapping
    public ListaEspera guardar(@RequestBody ListaEspera listaEspera) {
        return listaEsperaService.guardar(listaEspera);
    }
}