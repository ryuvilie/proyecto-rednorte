package com.rednorte.gestionclinica.strategy;

import com.rednorte.gestionclinica.model.ListaEspera;
import org.springframework.stereotype.Component;

@Component
public class PrioridadMediaStrategy implements PrioridadStrategy {

    @Override
    public void aplicarPrioridad(ListaEspera listaEspera) {
        listaEspera.setPrioridad("Media");
        listaEspera.setEstado("Pendiente");
    }
}