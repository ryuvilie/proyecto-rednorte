package com.rednorte.gestionclinica.service;

import com.rednorte.gestionclinica.model.ListaEspera;
import com.rednorte.gestionclinica.repository.ListaEsperaRepository;
import com.rednorte.gestionclinica.strategy.PrioridadStrategy;
import com.rednorte.gestionclinica.strategy.PrioridadStrategyFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaEsperaService {

    private final ListaEsperaRepository listaEsperaRepository;
    private final PrioridadStrategyFactory prioridadStrategyFactory;

    public ListaEsperaService(
            ListaEsperaRepository listaEsperaRepository,
            PrioridadStrategyFactory prioridadStrategyFactory
    ) {
        this.listaEsperaRepository = listaEsperaRepository;
        this.prioridadStrategyFactory = prioridadStrategyFactory;
    }

    public List<ListaEspera> listar() {
        return listaEsperaRepository.findAll();
    }

    public ListaEspera guardar(ListaEspera listaEspera) {

        PrioridadStrategy strategy =
                prioridadStrategyFactory.obtenerEstrategia(listaEspera.getPrioridad());

        strategy.aplicarPrioridad(listaEspera);

        return listaEsperaRepository.save(listaEspera);
    }
}