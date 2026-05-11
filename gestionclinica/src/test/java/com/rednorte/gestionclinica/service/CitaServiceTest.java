package com.rednorte.gestionclinica.service;

import com.rednorte.gestionclinica.model.Cita;
import com.rednorte.gestionclinica.model.Doctor;
import com.rednorte.gestionclinica.repository.CitaRepository;
import com.rednorte.gestionclinica.repository.DoctorRepository;
import com.rednorte.gestionclinica.repository.ListaEsperaRepository;
import com.rednorte.gestionclinica.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CitaServiceTest {

    private CitaRepository citaRepository;
    private DoctorRepository doctorRepository;
    private ListaEsperaRepository listaEsperaRepository;
    private PacienteRepository pacienteRepository;
    private CitaService citaService;

    @BeforeEach
    void setUp() {
        citaRepository = Mockito.mock(CitaRepository.class);
        doctorRepository = Mockito.mock(DoctorRepository.class);
        listaEsperaRepository = Mockito.mock(ListaEsperaRepository.class);
        pacienteRepository = Mockito.mock(PacienteRepository.class);

        citaService = new CitaService(
                citaRepository,
                doctorRepository,
                listaEsperaRepository,
                pacienteRepository
        );
    }

    @Test
    void crearDisponibilidadDebeLanzarErrorSiFechaEsNula() {
        Cita cita = new Cita();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            citaService.crearDisponibilidad(cita);
        });

        assertEquals("La fecha de la cita es obligatoria", exception.getMessage());

        verify(citaRepository, never()).save(any(Cita.class));
    }

    @Test
    void crearDisponibilidadDebeLanzarErrorSiFechaEsPasada() {
        Cita cita = new Cita();
        cita.setFechaCita(LocalDate.now().minusDays(1));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            citaService.crearDisponibilidad(cita);
        });

        assertEquals("La fecha de la cita no puede ser pasada", exception.getMessage());

        verify(citaRepository, never()).save(any(Cita.class));
    }

    @Test
    void crearDisponibilidadDebeLanzarErrorSiNoTieneDoctor() {
        Cita cita = new Cita();
        cita.setFechaCita(LocalDate.now().plusDays(1));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            citaService.crearDisponibilidad(cita);
        });

        assertEquals("La cita debe tener un doctor asignado", exception.getMessage());

        verify(citaRepository, never()).save(any(Cita.class));
    }

    @Test
    void crearDisponibilidadDebeLanzarErrorSiDoctorNoExiste() {
        Doctor doctor = new Doctor();
        doctor.setId(99L);

        Cita cita = new Cita();
        cita.setFechaCita(LocalDate.now().plusDays(1));
        cita.setDoctor(doctor);

        when(doctorRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            citaService.crearDisponibilidad(cita);
        });

        assertEquals("Doctor no encontrado", exception.getMessage());

        verify(citaRepository, never()).save(any(Cita.class));
    }

    @Test
    void crearDisponibilidadDebeGuardarCitaDisponible() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);
        doctor.setNombre("Ana");
        doctor.setApellido("Lopez");
        doctor.setEspecialidad("Cardiología");

        Cita cita = new Cita();
        cita.setFechaCita(LocalDate.now().plusDays(1));
        cita.setHoraCita(LocalTime.of(10, 30));
        cita.setEstablecimiento("Hospital RedNorte");
        cita.setDoctor(doctor);

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(citaRepository.save(any(Cita.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cita resultado = citaService.crearDisponibilidad(cita);

        assertNotNull(resultado);
        assertEquals("DISPONIBLE", resultado.getEstadoCita());
        assertNull(resultado.getListaEspera());
        assertEquals(doctor, resultado.getDoctor());

        verify(doctorRepository, times(1)).findById(1L);
        verify(citaRepository, times(1)).save(cita);
    }
}