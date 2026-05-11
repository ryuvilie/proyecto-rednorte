package com.rednorte.gestionclinica.strategy;

import com.rednorte.gestionclinica.model.ListaEspera;

public interface PrioridadStrategy {
    void aplicarPrioridad(ListaEspera listaEspera);
}