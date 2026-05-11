package com.rednorte.gestionsoporte.service;

import com.rednorte.gestionsoporte.model.Reporte;
import com.rednorte.gestionsoporte.repository.ReporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReporteServiceTest {

    private ReporteRepository reporteRepository;
    private ReporteService reporteService;

    @BeforeEach
    void setUp() {
        reporteRepository = mock(ReporteRepository.class);
        reporteService = new ReporteService(reporteRepository);
    }

    @Test
    void listarDebeRetornarReportes() {

        Reporte r1 = new Reporte();
        Reporte r2 = new Reporte();

        when(reporteRepository.findAll())
                .thenReturn(List.of(r1, r2));

        List<Reporte> resultado =
                reporteService.listar();

        assertEquals(2, resultado.size());

        verify(reporteRepository, times(1))
                .findAll();
    }

    @Test
    void guardarDebeGuardarReporte() {

        Reporte reporte = new Reporte();

        when(reporteRepository.save(any(Reporte.class)))
                .thenReturn(reporte);

        Reporte resultado =
                reporteService.guardar(reporte);

        assertEquals(reporte, resultado);

        ArgumentCaptor<Reporte> captor =
                ArgumentCaptor.forClass(Reporte.class);

        verify(reporteRepository)
                .save(captor.capture());

        assertEquals(reporte, captor.getValue());
    }
}