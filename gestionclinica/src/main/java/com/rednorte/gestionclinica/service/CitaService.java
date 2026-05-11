package com.rednorte.gestionclinica.service;

import com.rednorte.gestionclinica.model.Cita;
import com.rednorte.gestionclinica.model.Doctor;
import com.rednorte.gestionclinica.model.ListaEspera;
import com.rednorte.gestionclinica.model.Paciente;
import com.rednorte.gestionclinica.repository.CitaRepository;
import com.rednorte.gestionclinica.repository.DoctorRepository;
import com.rednorte.gestionclinica.repository.ListaEsperaRepository;
import com.rednorte.gestionclinica.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final DoctorRepository doctorRepository;
    private final ListaEsperaRepository listaEsperaRepository;
    private final PacienteRepository pacienteRepository;

    public CitaService(
            CitaRepository citaRepository,
            DoctorRepository doctorRepository,
            ListaEsperaRepository listaEsperaRepository,
            PacienteRepository pacienteRepository
    ) {
        this.citaRepository = citaRepository;
        this.doctorRepository = doctorRepository;
        this.listaEsperaRepository = listaEsperaRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public List<Cita> listar() {
        return citaRepository.findAll();
    }

    public List<Cita> listarDisponibles() {
        return citaRepository.findByEstadoCita("DISPONIBLE");
    }

    public List<Cita> listarPorDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        return citaRepository.findByDoctor(doctor);
    }

    public Cita crearDisponibilidad(Cita cita) {

        if (cita.getFechaCita() == null) {
            throw new RuntimeException("La fecha de la cita es obligatoria");
        }

        if (cita.getFechaCita().isBefore(LocalDate.now())) {
            throw new RuntimeException("La fecha de la cita no puede ser pasada");
        }

        if (cita.getDoctor() == null || cita.getDoctor().getId() == null) {
            throw new RuntimeException("La cita debe tener un doctor asignado");
        }

        Doctor doctor = doctorRepository.findById(cita.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));

        cita.setDoctor(doctor);
        cita.setEstadoCita("DISPONIBLE");
        cita.setListaEspera(null);

        return citaRepository.save(cita);
    }

    public Cita reservarCita(Long citaId, Long listaEsperaId) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        ListaEspera listaEspera = listaEsperaRepository.findById(listaEsperaId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        if (!"DISPONIBLE".equalsIgnoreCase(cita.getEstadoCita())) {
            throw new RuntimeException("La cita ya no está disponible");
        }

        if (cita.getFechaCita() != null && cita.getFechaCita().isBefore(LocalDate.now())) {
            throw new RuntimeException("No se puede reservar una cita con fecha pasada");
        }

        cita.setListaEspera(listaEspera);
        cita.setEstadoCita("ASIGNADA");

        return citaRepository.save(cita);
    }

    public Cita reservarCitaPaciente(Long citaId, Long pacienteId) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (!"DISPONIBLE".equalsIgnoreCase(cita.getEstadoCita())) {
            throw new RuntimeException("La cita ya no está disponible");
        }

        if (cita.getFechaCita() != null && cita.getFechaCita().isBefore(LocalDate.now())) {
            throw new RuntimeException("No se puede reservar una cita con fecha pasada");
        }

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        ListaEspera listaEspera = new ListaEspera();
        listaEspera.setPaciente(paciente);
        listaEspera.setEspecialidad(
                cita.getDoctor() != null ? cita.getDoctor().getEspecialidad() : "Consulta general"
        );
        listaEspera.setPrioridad("BAJA");
        listaEspera.setEstado("CITA_ASIGNADA");
        listaEspera.setFechaIngreso(LocalDate.now());

        ListaEspera listaGuardada = listaEsperaRepository.save(listaEspera);

        cita.setListaEspera(listaGuardada);
        cita.setEstadoCita("ASIGNADA");

        return citaRepository.save(cita);
    }
}