package com.rednorte.gestionclinica.strategy;

import com.rednorte.gestionclinica.model.ListaEspera;
import org.springframework.stereotype.Component;

@Component
public class PrioridadBajaStrategy implements PrioridadStrategy {

    @Override
    public void aplicarPrioridad(ListaEspera listaEspera) {
        listaEspera.setPrioridad("Baja");
        listaEspera.setEstado("No urgente");
    }
}