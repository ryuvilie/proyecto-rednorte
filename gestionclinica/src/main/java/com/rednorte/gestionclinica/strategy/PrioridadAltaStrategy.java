package com.rednorte.gestionclinica.strategy;

import com.rednorte.gestionclinica.model.ListaEspera;
import org.springframework.stereotype.Component;

@Component
public class PrioridadAltaStrategy implements PrioridadStrategy {

    @Override
    public void aplicarPrioridad(ListaEspera listaEspera) {
        listaEspera.setPrioridad("Alta");
        listaEspera.setEstado("Prioritaria");
    }
}