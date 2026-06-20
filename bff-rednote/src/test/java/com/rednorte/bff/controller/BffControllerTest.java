package com.rednorte.bff.controller;

import com.rednorte.bff.service.BffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BffControllerTest {

    private BffService bffService;
    private BffController bffController;

    @BeforeEach
    void setUp() {
        bffService = mock(BffService.class);
        bffController = new BffController(bffService);
    }

    @Test
    void loginDebeLlamarAlServicio() {
        Object request = new Object();
        Object response = new Object();

        when(bffService.login(request)).thenReturn(response);

        Object resultado = bffController.login(request);

        assertEquals(response, resultado);
        verify(bffService, times(1)).login(request);
    }

    @Test
    void registerDebeLlamarAlServicio() {
        Object request = new Object();
        Object response = new Object();

        when(bffService.register(request)).thenReturn(response);

        Object resultado = bffController.register(request);

        assertEquals(response, resultado);
        verify(bffService, times(1)).register(request);
    }

    @Test
    void obtenerUsuariosDebeLlamarAlServicio() {
        Object response = new Object();

        when(bffService.obtenerUsuarios()).thenReturn(response);

        Object resultado = bffController.obtenerUsuarios();

        assertEquals(response, resultado);
        verify(bffService, times(1)).obtenerUsuarios();
    }

    @Test
    void cambiarRolUsuarioDebeLlamarAlServicio() {
        Long id = 1L;
        Object body = new Object();
        Object response = new Object();

        when(bffService.cambiarRolUsuario(id, body)).thenReturn(response);

        Object resultado = bffController.cambiarRolUsuario(id, body);

        assertEquals(response, resultado);
        verify(bffService, times(1)).cambiarRolUsuario(id, body);
    }

    @Test
    void cambiarEstadoUsuarioDebeLlamarAlServicio() {
        Long id = 1L;
        Object body = new Object();
        Object response = new Object();

        when(bffService.cambiarEstadoUsuario(id, body)).thenReturn(response);

        Object resultado = bffController.cambiarEstadoUsuario(id, body);

        assertEquals(response, resultado);
        verify(bffService, times(1)).cambiarEstadoUsuario(id, body);
    }

    @Test
    void actualizarPermisosUsuarioDebeLlamarAlServicio() {
        Long id = 1L;
        Object body = new Object();
        Object response = new Object();

        when(bffService.actualizarPermisosUsuario(id, body)).thenReturn(response);

        Object resultado = bffController.actualizarPermisosUsuario(id, body);

        assertEquals(response, resultado);
        verify(bffService, times(1)).actualizarPermisosUsuario(id, body);
    }

    @Test
    void obtenerPacientesDebeLlamarAlServicio() {
        Object response = new Object();

        when(bffService.obtenerPacientes()).thenReturn(response);

        Object resultado = bffController.obtenerPacientes();

        assertEquals(response, resultado);
        verify(bffService, times(1)).obtenerPacientes();
    }

    @Test
    void crearPacienteDebeLlamarAlServicio() {
        Object paciente = new Object();
        Object response = new Object();

        when(bffService.crearPaciente(paciente)).thenReturn(response);

        Object resultado = bffController.crearPaciente(paciente);

        assertEquals(response, resultado);
        verify(bffService, times(1)).crearPaciente(paciente);
    }

    @Test
    void eliminarPacienteDebeLlamarAlServicio() {
        Long id = 1L;

        bffController.eliminarPaciente(id);

        verify(bffService, times(1)).eliminarPaciente(id);
    }

    @Test
    void limpiarDependenciasPacienteDebeLlamarAlServicio() {
        Long id = 1L;

        bffController.limpiarDependenciasPaciente(id);

        verify(bffService, times(1)).limpiarDependenciasPaciente(id);
    }

    @Test
    void obtenerDoctoresDebeLlamarAlServicio() {
        Object response = new Object();

        when(bffService.obtenerDoctores()).thenReturn(response);

        Object resultado = bffController.obtenerDoctores();

        assertEquals(response, resultado);
        verify(bffService, times(1)).obtenerDoctores();
    }

    @Test
    void obtenerListaEsperaDebeLlamarAlServicio() {
        Object response = new Object();

        when(bffService.obtenerListaEspera()).thenReturn(response);

        Object resultado = bffController.obtenerListaEspera();

        assertEquals(response, resultado);
        verify(bffService, times(1)).obtenerListaEspera();
    }

    @Test
    void crearListaEsperaDebeLlamarAlServicio() {
        Object listaEspera = new Object();
        Object response = new Object();

        when(bffService.crearListaEspera(listaEspera)).thenReturn(response);

        Object resultado = bffController.crearListaEspera(listaEspera);

        assertEquals(response, resultado);
        verify(bffService, times(1)).crearListaEspera(listaEspera);
    }

    @Test
    void obtenerCitasDebeLlamarAlServicio() {
        Object response = new Object();

        when(bffService.obtenerCitas()).thenReturn(response);

        Object resultado = bffController.obtenerCitas();

        assertEquals(response, resultado);
        verify(bffService, times(1)).obtenerCitas();
    }

    @Test
    void crearCitaDebeLlamarAlServicio() {
        Object cita = new Object();
        Object response = new Object();

        when(bffService.crearCita(cita)).thenReturn(response);

        Object resultado = bffController.crearCita(cita);

        assertEquals(response, resultado);
        verify(bffService, times(1)).crearCita(cita);
    }

    @Test
    void obtenerCitasDisponiblesDebeLlamarAlServicio() {
        Object response = new Object();

        when(bffService.obtenerCitasDisponibles()).thenReturn(response);

        Object resultado = bffController.obtenerCitasDisponibles();

        assertEquals(response, resultado);
        verify(bffService, times(1)).obtenerCitasDisponibles();
    }

    @Test
    void obtenerCitasDoctorDebeLlamarAlServicio() {
        Long doctorId = 1L;
        Object response = new Object();

        when(bffService.obtenerCitasDoctor(doctorId)).thenReturn(response);

        Object resultado = bffController.obtenerCitasDoctor(doctorId);

        assertEquals(response, resultado);
        verify(bffService, times(1)).obtenerCitasDoctor(doctorId);
    }

    @Test
    void reservarCitaDebeLlamarAlServicio() {
        Long citaId = 1L;
        Long listaEsperaId = 2L;
        Object response = new Object();

        when(bffService.reservarCita(citaId, listaEsperaId)).thenReturn(response);

        Object resultado = bffController.reservarCita(citaId, listaEsperaId);

        assertEquals(response, resultado);
        verify(bffService, times(1)).reservarCita(citaId, listaEsperaId);
    }

    @Test
    void reservarCitaPacienteDebeLlamarAlServicio() {
        Long citaId = 1L;
        Long pacienteId = 2L;
        Object response = new Object();

        when(bffService.reservarCitaPaciente(citaId, pacienteId)).thenReturn(response);

        Object resultado = bffController.reservarCitaPaciente(citaId, pacienteId);

        assertEquals(response, resultado);
        verify(bffService, times(1)).reservarCitaPaciente(citaId, pacienteId);
    }

    @Test
    void eliminarCitaDebeLlamarAlServicio() {
        Long id = 1L;

        bffController.eliminarCita(id);

        verify(bffService, times(1)).eliminarCita(id);
    }

    @Test
    void obtenerNotificacionesDebeLlamarAlServicio() {
        Object response = new Object();

        when(bffService.obtenerNotificaciones()).thenReturn(response);

        Object resultado = bffController.obtenerNotificaciones();

        assertEquals(response, resultado);
        verify(bffService, times(1)).obtenerNotificaciones();
    }

    @Test
    void obtenerReasignacionesDebeLlamarAlServicio() {
        Object response = new Object();

        when(bffService.obtenerReasignaciones()).thenReturn(response);

        Object resultado = bffController.obtenerReasignaciones();

        assertEquals(response, resultado);
        verify(bffService, times(1)).obtenerReasignaciones();
    }

    @Test
    void obtenerReportesDebeLlamarAlServicio() {
        Object response = new Object();

        when(bffService.obtenerReportes()).thenReturn(response);

        Object resultado = bffController.obtenerReportes();

        assertEquals(response, resultado);
        verify(bffService, times(1)).obtenerReportes();
    }
}