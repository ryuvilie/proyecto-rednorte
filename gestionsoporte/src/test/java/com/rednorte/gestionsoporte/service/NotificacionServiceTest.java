package com.rednorte.gestionsoporte.service;

import com.rednorte.gestionsoporte.model.Notificacion;
import com.rednorte.gestionsoporte.repository.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NotificacionServiceTest {

    private NotificacionRepository notificacionRepository;
    private NotificacionService notificacionService;

    @BeforeEach
    void setUp() {
        notificacionRepository = mock(NotificacionRepository.class);
        notificacionService = new NotificacionService(notificacionRepository);
    }

    @Test
    void listarDebeRetornarNotificaciones() {

        Notificacion n1 = new Notificacion();
        Notificacion n2 = new Notificacion();

        when(notificacionRepository.findAll())
                .thenReturn(List.of(n1, n2));

        List<Notificacion> resultado = notificacionService.listar();

        assertEquals(2, resultado.size());

        verify(notificacionRepository, times(1)).findAll();
    }

    @Test
    void guardarDebeGuardarNotificacion() {

        Notificacion notificacion = new Notificacion();

        when(notificacionRepository.save(any(Notificacion.class)))
                .thenReturn(notificacion);

        Notificacion resultado =
                notificacionService.guardar(notificacion);

        assertEquals(notificacion, resultado);

        ArgumentCaptor<Notificacion> captor =
                ArgumentCaptor.forClass(Notificacion.class);

        verify(notificacionRepository)
                .save(captor.capture());

        assertEquals(notificacion, captor.getValue());
    }
}