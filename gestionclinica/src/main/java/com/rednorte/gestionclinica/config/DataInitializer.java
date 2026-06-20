package com.rednorte.gestionclinica.config;

import com.rednorte.gestionclinica.model.Doctor;
import com.rednorte.gestionclinica.model.EstadoUsuario;
import com.rednorte.gestionclinica.model.Paciente;
import com.rednorte.gestionclinica.model.RolUsuario;
import com.rednorte.gestionclinica.model.Usuario;
import com.rednorte.gestionclinica.repository.DoctorRepository;
import com.rednorte.gestionclinica.repository.PacienteRepository;
import com.rednorte.gestionclinica.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.rednorte.gestionclinica.model.Cita;
import com.rednorte.gestionclinica.repository.CitaRepository;

import java.time.LocalTime;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final DoctorRepository doctorRepository;
    private final PacienteRepository pacienteRepository;
    private final CitaRepository citaRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public DataInitializer(
            UsuarioRepository usuarioRepository,
            DoctorRepository doctorRepository,
            PacienteRepository pacienteRepository,
            CitaRepository citaRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.doctorRepository = doctorRepository;
        this.pacienteRepository = pacienteRepository;
        this.citaRepository = citaRepository;
    }

    @Override
    public void run(String... args) {

        crearAdminInicial();

        crearDoctorConUsuario(
                "Carolina",
                "Muñoz",
                "Cardiología",
                "doctor1@rednorte.cl",
                "912345678",
                "Dra. Carolina Muñoz"
        );

        crearDoctoresDemo();

        crearPacientesDemo();

        crearCitasDemo();

        System.out.println("Datos iniciales RedNorte verificados correctamente.");
    }

    // =========================
    // ADMIN INICIAL
    // =========================

    private void crearAdminInicial() {

        String correoAdmin = "admin@rednorte.cl";

        if (!usuarioRepository.existsByCorreo(correoAdmin)) {

            Usuario admin = new Usuario();

            admin.setNombre("Administrador Clínica");
            admin.setCorreo(correoAdmin);
            admin.setPassword(passwordEncoder.encode("1234"));
            admin.setRol(RolUsuario.ADMIN_CLINICA);
            admin.setEstado(EstadoUsuario.ACTIVO);

            admin.setPuedeGestionarUsuarios(true);
            admin.setPuedeGestionarPacientes(true);
            admin.setPuedeGestionarCitas(true);
            admin.setPuedeGestionarListaEspera(true);
            admin.setPuedeGestionarReportes(true);

            usuarioRepository.save(admin);

            System.out.println("Usuario administrador creado correctamente.");
        }
    }

    // =========================
    // DOCTORES DEMO CON USUARIO
    // =========================

    private void crearDoctoresDemo() {

        crearDoctorConUsuario(
                "Andrés",
                "Pérez",
                "Traumatología",
                "andres.perez@rednorte.cl",
                "922222222",
                "Dr. Andrés Pérez"
        );

        crearDoctorConUsuario(
                "Sofía",
                "Ramírez",
                "Neurología",
                "sofia.ramirez@rednorte.cl",
                "922333333",
                "Dra. Sofía Ramírez"
        );

        crearDoctorConUsuario(
                "Felipe",
                "Contreras",
                "Dermatología",
                "felipe.contreras@rednorte.cl",
                "922444444",
                "Dr. Felipe Contreras"
        );

        crearDoctorConUsuario(
                "Paula",
                "Fuentes",
                "Medicina General",
                "paula.fuentes@rednorte.cl",
                "922555555",
                "Dra. Paula Fuentes"
        );

        System.out.println("Doctores demo verificados correctamente.");
    }

    private void crearDoctorConUsuario(
            String nombre,
            String apellido,
            String especialidad,
            String correo,
            String telefono,
            String nombreUsuario
    ) {

        Doctor doctor = obtenerOCrearDoctor(
                nombre,
                apellido,
                especialidad,
                correo,
                telefono
        );

        if (!usuarioRepository.existsByCorreo(correo)) {

            Usuario usuarioDoctor = new Usuario();

            usuarioDoctor.setNombre(nombreUsuario);
            usuarioDoctor.setCorreo(correo);
            usuarioDoctor.setPassword(passwordEncoder.encode("1234"));

            usuarioDoctor.setRol(RolUsuario.DOCTOR);
            usuarioDoctor.setEstado(EstadoUsuario.ACTIVO);

            usuarioDoctor.setDoctor(doctor);

            usuarioDoctor.setPuedeGestionarUsuarios(false);
            usuarioDoctor.setPuedeGestionarPacientes(false);
            usuarioDoctor.setPuedeGestionarCitas(false);
            usuarioDoctor.setPuedeGestionarListaEspera(false);
            usuarioDoctor.setPuedeGestionarReportes(false);

            usuarioRepository.save(usuarioDoctor);
        }
    }

    private Doctor obtenerOCrearDoctor(
            String nombre,
            String apellido,
            String especialidad,
            String correo,
            String telefono
    ) {
        return doctorRepository.findAll()
                .stream()
                .filter(doctor -> correo.equals(doctor.getCorreo()))
                .findFirst()
                .orElseGet(() -> {
                    Doctor doctor = new Doctor();

                    doctor.setNombre(nombre);
                    doctor.setApellido(apellido);
                    doctor.setEspecialidad(especialidad);
                    doctor.setCorreo(correo);
                    doctor.setTelefono(telefono);
                    doctor.setActivo(true);

                    return doctorRepository.save(doctor);
                });
    }

    // =========================
    // PACIENTES DEMO CON USUARIO
    // =========================

    private void crearPacientesDemo() {

        crearPacienteConUsuario(
                "11111111-1",
                "María",
                "González",
                "maria.gonzalez@rednorte.cl",
                "912345678",
                LocalDate.of(1990, 3, 15)
        );

        crearPacienteConUsuario(
                "22222222-2",
                "Carlos",
                "Muñoz",
                "carlos.munoz@rednorte.cl",
                "923456789",
                LocalDate.of(1985, 7, 22)
        );

        crearPacienteConUsuario(
                "33333333-3",
                "Ana",
                "Rojas",
                "ana.rojas@rednorte.cl",
                "934567890",
                LocalDate.of(1998, 11, 5)
        );

        crearPacienteConUsuario(
                "44444444-4",
                "Pedro",
                "Silva",
                "pedro.silva@rednorte.cl",
                "945678901",
                LocalDate.of(1979, 1, 30)
        );

        crearPacienteConUsuario(
                "55555555-5",
                "Camila",
                "Torres",
                "camila.torres@rednorte.cl",
                "956789012",
                LocalDate.of(1995, 9, 12)
        );

        crearPacienteConUsuario(
                "66666666-6",
                "Jorge",
                "Herrera",
                "jorge.herrera@rednorte.cl",
                "967890123",
                LocalDate.of(1968, 4, 18)
        );

        crearPacienteConUsuario(
                "77777777-7",
                "Valentina",
                "Castro",
                "valentina.castro@rednorte.cl",
                "978901234",
                LocalDate.of(2001, 6, 25)
        );

        crearPacienteConUsuario(
                "88888888-8",
                "Luis",
                "Morales",
                "luis.morales@rednorte.cl",
                "989012345",
                LocalDate.of(1982, 12, 9)
        );

        crearPacienteConUsuario(
                "99999999-9",
                "Fernanda",
                "Vega",
                "fernanda.vega@rednorte.cl",
                "990123456",
                LocalDate.of(1992, 8, 14)
        );

        crearPacienteConUsuario(
                "12345678-5",
                "Diego",
                "Navarro",
                "diego.navarro@rednorte.cl",
                "901234567",
                LocalDate.of(1975, 10, 3)
        );

        System.out.println("Pacientes demo verificados correctamente.");
    }

    private void crearPacienteConUsuario(
            String rut,
            String nombre,
            String apellido,
            String correo,
            String telefono,
            LocalDate fechaNacimiento
    ) {

        Paciente paciente = obtenerOCrearPaciente(
                rut,
                nombre,
                apellido,
                correo,
                telefono,
                fechaNacimiento
        );

        if (!usuarioRepository.existsByCorreo(correo)) {

            Usuario usuarioPaciente = new Usuario();

            usuarioPaciente.setNombre(nombre + " " + apellido);
            usuarioPaciente.setCorreo(correo);
            usuarioPaciente.setPassword(passwordEncoder.encode("1234"));

            usuarioPaciente.setRol(RolUsuario.PACIENTE);
            usuarioPaciente.setEstado(EstadoUsuario.ACTIVO);

            usuarioPaciente.setPaciente(paciente);

            usuarioPaciente.setPuedeGestionarUsuarios(false);
            usuarioPaciente.setPuedeGestionarPacientes(false);
            usuarioPaciente.setPuedeGestionarCitas(false);
            usuarioPaciente.setPuedeGestionarListaEspera(false);
            usuarioPaciente.setPuedeGestionarReportes(false);

            usuarioRepository.save(usuarioPaciente);
        }
    }

    private Paciente obtenerOCrearPaciente(
            String rut,
            String nombre,
            String apellido,
            String correo,
            String telefono,
            LocalDate fechaNacimiento
    ) {
        return pacienteRepository.findAll()
                .stream()
                .filter(paciente -> rut.equals(paciente.getRut()))
                .findFirst()
                .orElseGet(() -> {
                    Paciente paciente = new Paciente();

                    paciente.setRut(rut);
                    paciente.setNombre(nombre);
                    paciente.setApellido(apellido);
                    paciente.setCorreo(correo);
                    paciente.setTelefono(telefono);
                    paciente.setFechaNacimiento(fechaNacimiento);

                    return pacienteRepository.save(paciente);
                });
    }
        private void crearCitasDemo() {

        crearCitaDisponibleSiNoExiste(
                "doctor1@rednorte.cl",
                "Hospital RedNorte Central",
                "Dra. Carolina Muñoz",
                LocalDate.now().plusDays(1),
                LocalTime.of(9, 0)
        );

        crearCitaDisponibleSiNoExiste(
                "doctor1@rednorte.cl",
                "Hospital RedNorte Central",
                "Dra. Carolina Muñoz",
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0)
        );

        crearCitaDisponibleSiNoExiste(
                "andres.perez@rednorte.cl",
                "Centro Médico Norte",
                "Dr. Andrés Pérez",
                LocalDate.now().plusDays(2),
                LocalTime.of(9, 30)
        );

        crearCitaDisponibleSiNoExiste(
                "andres.perez@rednorte.cl",
                "Centro Médico Norte",
                "Dr. Andrés Pérez",
                LocalDate.now().plusDays(2),
                LocalTime.of(11, 0)
        );

        crearCitaDisponibleSiNoExiste(
                "sofia.ramirez@rednorte.cl",
                "Clínica Especializada RedNorte",
                "Dra. Sofía Ramírez",
                LocalDate.now().plusDays(3),
                LocalTime.of(8, 30)
        );

        crearCitaDisponibleSiNoExiste(
                "sofia.ramirez@rednorte.cl",
                "Clínica Especializada RedNorte",
                "Dra. Sofía Ramírez",
                LocalDate.now().plusDays(3),
                LocalTime.of(12, 0)
        );

        crearCitaDisponibleSiNoExiste(
                "felipe.contreras@rednorte.cl",
                "Hospital RedNorte Central",
                "Dr. Felipe Contreras",
                LocalDate.now().plusDays(4),
                LocalTime.of(14, 0)
        );

        crearCitaDisponibleSiNoExiste(
                "felipe.contreras@rednorte.cl",
                "Hospital RedNorte Central",
                "Dr. Felipe Contreras",
                LocalDate.now().plusDays(4),
                LocalTime.of(15, 30)
        );

        crearCitaDisponibleSiNoExiste(
                "paula.fuentes@rednorte.cl",
                "CESFAM RedNorte",
                "Dra. Paula Fuentes",
                LocalDate.now().plusDays(5),
                LocalTime.of(10, 15)
        );

        crearCitaDisponibleSiNoExiste(
                "paula.fuentes@rednorte.cl",
                "CESFAM RedNorte",
                "Dra. Paula Fuentes",
                LocalDate.now().plusDays(5),
                LocalTime.of(11, 15)
        );

        System.out.println("Citas demo verificadas correctamente.");
    }

    private void crearCitaDisponibleSiNoExiste(
            String correoDoctor,
            String establecimiento,
            String medico,
            LocalDate fechaCita,
            LocalTime horaCita
    ) {
        Doctor doctor = doctorRepository.findAll()
                .stream()
                .filter(d -> correoDoctor.equals(d.getCorreo()))
                .findFirst()
                .orElse(null);

        if (doctor == null) {
            return;
        }

        boolean citaExiste = citaRepository.findAll()
                .stream()
                .anyMatch(cita ->
                        cita.getDoctor() != null &&
                        cita.getDoctor().getId().equals(doctor.getId()) &&
                        cita.getFechaCita().equals(fechaCita) &&
                        cita.getHoraCita().equals(horaCita)
                );

        if (!citaExiste) {

            Cita cita = new Cita();

            cita.setEstablecimiento(establecimiento);
            // cita.setMedico(medico);
            cita.setFechaCita(fechaCita);
            cita.setHoraCita(horaCita);
            cita.setEstadoCita("DISPONIBLE");
            cita.setDoctor(doctor);

            citaRepository.save(cita);
        }
    }
}