package com.rednorte.bff.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BffServiceTest {

    private RestTemplate restTemplate;
    private BffService bffService;

    private final String gestionClinicaUrl = "http://gestionclinica:8081";
    private final String gestionSoporteUrl = "http://gestionsoporte:8082";

    @BeforeEach
    void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        bffService = new BffService(restTemplate);

        ReflectionTestUtils.setField(bffService, "gestionClinicaUrl", gestionClinicaUrl);
        ReflectionTestUtils.setField(bffService, "gestionSoporteUrl", gestionSoporteUrl);
    }

    @Test
    void obtenerPacientesDebeLlamarAGestionClinica() {
        Object respuesta = new Object();

        when(restTemplate.getForObject(
                gestionClinicaUrl + "/pacientes",
                Object.class
        )).thenReturn(respuesta);

        Object resultado = bffService.obtenerPacientes();

        assertEquals(respuesta, resultado);

        verify(restTemplate, times(1))
                .getForObject(gestionClinicaUrl + "/pacientes", Object.class);
    }

    @Test
    void crearPacienteDebeEnviarPacienteAGestionClinica() {
        Map<String, Object> paciente = new HashMap<>();
        paciente.put("nombre", "Juan");

        Object respuesta = new Object();

        when(restTemplate.postForObject(
                gestionClinicaUrl + "/pacientes",
                paciente,
                Object.class
        )).thenReturn(respuesta);

        Object resultado = bffService.crearPaciente(paciente);

        assertEquals(respuesta, resultado);

        verify(restTemplate, times(1))
                .postForObject(gestionClinicaUrl + "/pacientes", paciente, Object.class);
    }

    @Test
    void eliminarPacienteDebeLlamarDelete() {
        Long pacienteId = 1L;

        bffService.eliminarPaciente(pacienteId);

        verify(restTemplate, times(1))
                .delete(gestionClinicaUrl + "/pacientes/" + pacienteId);
    }

    @Test
    void obtenerCitasDebeLlamarAGestionClinica() {
        Object respuesta = new Object();

        when(restTemplate.getForObject(
                gestionClinicaUrl + "/citas",
                Object.class
        )).thenReturn(respuesta);

        Object resultado = bffService.obtenerCitas();

        assertEquals(respuesta, resultado);

        verify(restTemplate, times(1))
                .getForObject(gestionClinicaUrl + "/citas", Object.class);
    }

    @Test
    void crearCitaDebeEnviarCitaAGestionClinica() {
        Map<String, Object> cita = new HashMap<>();
        cita.put("estadoCita", "DISPONIBLE");

        Object respuesta = new Object();

        when(restTemplate.postForObject(
                gestionClinicaUrl + "/citas",
                cita,
                Object.class
        )).thenReturn(respuesta);

        Object resultado = bffService.crearCita(cita);

        assertEquals(respuesta, resultado);

        verify(restTemplate, times(1))
                .postForObject(gestionClinicaUrl + "/citas", cita, Object.class);
    }

    @Test
    void reservarCitaDebeLlamarPutConRespuesta() {
        Long citaId = 1L;
        Long listaEsperaId = 2L;

        ResponseEntity<Object> response =
                new ResponseEntity<>("Reserva creada", HttpStatus.OK);

        when(restTemplate.exchange(
                eq(gestionClinicaUrl + "/citas/" + citaId + "/reservar/" + listaEsperaId),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(Object.class)
        )).thenReturn(response);

        Object resultado = bffService.reservarCita(citaId, listaEsperaId);

        assertEquals("Reserva creada", resultado);

        verify(restTemplate, times(1)).exchange(
                eq(gestionClinicaUrl + "/citas/" + citaId + "/reservar/" + listaEsperaId),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(Object.class)
        );
    }

    @Test
    void obtenerNotificacionesDebeLlamarAGestionSoporte() {
        Object respuesta = new Object();

        when(restTemplate.getForObject(
                gestionSoporteUrl + "/notificaciones",
                Object.class
        )).thenReturn(respuesta);

        Object resultado = bffService.obtenerNotificaciones();

        assertEquals(respuesta, resultado);

        verify(restTemplate, times(1))
                .getForObject(gestionSoporteUrl + "/notificaciones", Object.class);
    }

    @Test
    void obtenerReportesDebeLlamarAGestionSoporte() {
        Object respuesta = new Object();

        when(restTemplate.getForObject(
                gestionSoporteUrl + "/reportes",
                Object.class
        )).thenReturn(respuesta);

        Object resultado = bffService.obtenerReportes();

        assertEquals(respuesta, resultado);

        verify(restTemplate, times(1))
                .getForObject(gestionSoporteUrl + "/reportes", Object.class);
    }
}