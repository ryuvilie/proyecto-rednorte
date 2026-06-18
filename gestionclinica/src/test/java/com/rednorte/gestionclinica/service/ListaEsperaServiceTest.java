package com.rednorte.gestionclinica.service;

import com.rednorte.gestionclinica.model.ListaEspera;
import com.rednorte.gestionclinica.repository.ListaEsperaRepository;
import com.rednorte.gestionclinica.strategy.PrioridadStrategy;
import com.rednorte.gestionclinica.strategy.PrioridadStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListaEsperaServiceTest {

    private ListaEsperaRepository listaEsperaRepository;
    private PrioridadStrategyFactory prioridadStrategyFactory;
    private PrioridadStrategy prioridadStrategy;

    private ListaEsperaService listaEsperaService;

    @BeforeEach
    void setUp() {

        listaEsperaRepository = mock(ListaEsperaRepository.class);

        prioridadStrategyFactory = mock(PrioridadStrategyFactory.class);

        prioridadStrategy = mock(PrioridadStrategy.class);

        listaEsperaService = new ListaEsperaService(
                listaEsperaRepository,
                prioridadStrategyFactory
        );
    }

    @Test
    void listarDebeRetornarListaDeEspera() {

        when(listaEsperaRepository.findAll())
                .thenReturn(new ArrayList<>());

        assertNotNull(listaEsperaService.listar());

        verify(listaEsperaRepository, times(1)).findAll();
    }

    @Test
    void guardarDebeAplicarStrategyYGuardar() {

        ListaEspera lista = new ListaEspera();

        lista.setPrioridad("Alta");

        when(prioridadStrategyFactory.obtenerEstrategia("Alta"))
                .thenReturn(prioridadStrategy);

        when(listaEsperaRepository.save(any(ListaEspera.class)))
                .thenReturn(lista);

        ListaEspera resultado = listaEsperaService.guardar(lista);

        assertNotNull(resultado);

        verify(prioridadStrategyFactory, times(1))
                .obtenerEstrategia("Alta");

        verify(prioridadStrategy, times(1))
                .aplicarPrioridad(lista);

        verify(listaEsperaRepository, times(1))
                .save(lista);
    }
}