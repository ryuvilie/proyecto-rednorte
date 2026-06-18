package com.rednorte.gestionclinica.service;

import com.rednorte.gestionclinica.model.Cita;
import com.rednorte.gestionclinica.model.Doctor;
import com.rednorte.gestionclinica.model.ListaEspera;
import com.rednorte.gestionclinica.model.Paciente;
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

    @Test
    void reservarCitaPacienteDebeCrearListaEsperaYAsignarCita() {
        Long citaId = 1L;
        Long pacienteId = 10L;

        Doctor doctor = new Doctor();
        doctor.setId(5L);
        doctor.setNombre("Carlos");
        doctor.setApellido("Muñoz");
        doctor.setEspecialidad("Cardiología");

        Paciente paciente = new Paciente();
        paciente.setId(pacienteId);
        paciente.setNombre("María");
        paciente.setApellido("González");
        paciente.setRut("11111111-1");

        Cita cita = new Cita();
        cita.setId(citaId);
        cita.setFechaCita(LocalDate.now().plusDays(2));
        cita.setEstadoCita("DISPONIBLE");
        cita.setDoctor(doctor);

        ListaEspera listaGuardada = new ListaEspera();
        listaGuardada.setId(20L);
        listaGuardada.setPaciente(paciente);
        listaGuardada.setEspecialidad("Cardiología");
        listaGuardada.setPrioridad("BAJA");
        listaGuardada.setEstado("CITA_ASIGNADA");
        listaGuardada.setFechaIngreso(LocalDate.now());

        when(citaRepository.findById(citaId)).thenReturn(Optional.of(cita));
        when(pacienteRepository.findById(pacienteId)).thenReturn(Optional.of(paciente));
        when(listaEsperaRepository.save(any(ListaEspera.class))).thenReturn(listaGuardada);
        when(citaRepository.save(any(Cita.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cita resultado = citaService.reservarCitaPaciente(citaId, pacienteId);

        assertNotNull(resultado);
        assertEquals("ASIGNADA", resultado.getEstadoCita());
        assertNotNull(resultado.getListaEspera());
        assertEquals(paciente, resultado.getListaEspera().getPaciente());
        assertEquals("Cardiología", resultado.getListaEspera().getEspecialidad());
        assertEquals("CITA_ASIGNADA", resultado.getListaEspera().getEstado());

        verify(citaRepository, times(1)).findById(citaId);
        verify(pacienteRepository, times(1)).findById(pacienteId);
        verify(listaEsperaRepository, times(1)).save(any(ListaEspera.class));
        verify(citaRepository, times(1)).save(cita);
    }
}