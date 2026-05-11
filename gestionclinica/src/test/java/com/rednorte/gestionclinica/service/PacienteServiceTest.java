package com.rednorte.gestionclinica.service;

import com.rednorte.gestionclinica.model.Paciente;
import com.rednorte.gestionclinica.repository.CitaRepository;
import com.rednorte.gestionclinica.repository.ListaEsperaRepository;
import com.rednorte.gestionclinica.repository.PacienteRepository;
import com.rednorte.gestionclinica.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacienteServiceTest {

    private PacienteRepository pacienteRepository;
    private ListaEsperaRepository listaEsperaRepository;
    private CitaRepository citaRepository;
    private UsuarioRepository usuarioRepository;
    private PacienteService pacienteService;

    @BeforeEach
    void setUp() {
        pacienteRepository = Mockito.mock(PacienteRepository.class);
        listaEsperaRepository = Mockito.mock(ListaEsperaRepository.class);
        citaRepository = Mockito.mock(CitaRepository.class);
        usuarioRepository = Mockito.mock(UsuarioRepository.class);

        pacienteService = new PacienteService(
                pacienteRepository,
                listaEsperaRepository,
                citaRepository,
                usuarioRepository
        );
    }

    @Test
    void listarDebeRetornarPacientes() {
        when(pacienteRepository.findAll()).thenReturn(new ArrayList<>());

        assertNotNull(pacienteService.listar());

        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    void guardarDebeLanzarErrorSiRutEsInvalido() {
        Paciente paciente = new Paciente();
        paciente.setRut("12345678-9");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pacienteService.guardar(paciente);
        });

        assertEquals("El RUT ingresado no es válido", exception.getMessage());

        verify(pacienteRepository, never()).save(any(Paciente.class));
    }

    @Test
    void guardarDebeLanzarErrorSiRutYaExiste() {
        Paciente paciente = new Paciente();
        paciente.setRut("11111111-1");

        when(pacienteRepository.existsByRut("11111111-1")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pacienteService.guardar(paciente);
        });

        assertEquals("Ya existe un paciente registrado con ese RUT", exception.getMessage());

        verify(pacienteRepository, never()).save(any(Paciente.class));
    }

    @Test
    void eliminarDebeLimpiarDependenciasYEliminarPaciente() {
        Long pacienteId = 1L;

        when(listaEsperaRepository.findByPacienteId(pacienteId)).thenReturn(new ArrayList<>());

        pacienteService.eliminar(pacienteId);

        verify(listaEsperaRepository, times(1)).findByPacienteId(pacienteId);
        verify(listaEsperaRepository, times(1)).deleteAll(anyList());
        verify(pacienteRepository, times(1)).deleteById(pacienteId);
    }
}