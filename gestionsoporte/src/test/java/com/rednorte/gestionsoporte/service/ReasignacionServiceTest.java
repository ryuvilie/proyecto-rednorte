package com.rednorte.gestionsoporte.service;

import com.rednorte.gestionsoporte.model.Reasignacion;
import com.rednorte.gestionsoporte.repository.ReasignacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReasignacionServiceTest {

    private ReasignacionRepository reasignacionRepository;
    private ReasignacionService reasignacionService;

    @BeforeEach
    void setUp() {
        reasignacionRepository = mock(ReasignacionRepository.class);
        reasignacionService = new ReasignacionService(reasignacionRepository);
    }

    @Test
    void listarDebeRetornarReasignaciones() {

        Reasignacion r1 = new Reasignacion();
        Reasignacion r2 = new Reasignacion();

        when(reasignacionRepository.findAll())
                .thenReturn(List.of(r1, r2));

        List<Reasignacion> resultado =
                reasignacionService.listar();

        assertEquals(2, resultado.size());

        verify(reasignacionRepository, times(1))
                .findAll();
    }

    @Test
    void guardarDebeGuardarReasignacion() {

        Reasignacion reasignacion = new Reasignacion();

        when(reasignacionRepository.save(any(Reasignacion.class)))
                .thenReturn(reasignacion);

        Reasignacion resultado =
                reasignacionService.guardar(reasignacion);

        assertEquals(reasignacion, resultado);

        ArgumentCaptor<Reasignacion> captor =
                ArgumentCaptor.forClass(Reasignacion.class);

        verify(reasignacionRepository)
                .save(captor.capture());

        assertEquals(reasignacion, captor.getValue());
    }
}