package com.rednorte.gestionclinica.config;

import com.rednorte.gestionclinica.model.Doctor;
import com.rednorte.gestionclinica.model.EstadoUsuario;
import com.rednorte.gestionclinica.model.RolUsuario;
import com.rednorte.gestionclinica.model.Usuario;
import com.rednorte.gestionclinica.repository.DoctorRepository;
import com.rednorte.gestionclinica.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final DoctorRepository doctorRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public DataInitializer(
            UsuarioRepository usuarioRepository,
            DoctorRepository doctorRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void run(String... args) {

        // =========================
        // ADMIN INICIAL
        // =========================

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

        // =========================
        // DOCTOR INICIAL
        // =========================

        if (!doctorRepository.existsByCorreo("doctor1@rednorte.cl")) {

            Doctor doctor1 = new Doctor();

            doctor1.setNombre("Carolina");
            doctor1.setApellido("Muñoz");
            doctor1.setEspecialidad("Cardiología");
            doctor1.setCorreo("doctor1@rednorte.cl");
            doctor1.setTelefono("912345678");

            doctorRepository.save(doctor1);

            Usuario usuarioDoctor1 = new Usuario();

            usuarioDoctor1.setNombre("Dra. Carolina Muñoz");
            usuarioDoctor1.setCorreo("doctor1@rednorte.cl");
            usuarioDoctor1.setPassword(passwordEncoder.encode("1234"));

            usuarioDoctor1.setRol(RolUsuario.DOCTOR);
            usuarioDoctor1.setEstado(EstadoUsuario.ACTIVO);

            usuarioDoctor1.setDoctor(doctor1);

            usuarioDoctor1.setPuedeGestionarUsuarios(false);
            usuarioDoctor1.setPuedeGestionarPacientes(false);
            usuarioDoctor1.setPuedeGestionarCitas(false);
            usuarioDoctor1.setPuedeGestionarListaEspera(false);
            usuarioDoctor1.setPuedeGestionarReportes(false);

            usuarioRepository.save(usuarioDoctor1);

            System.out.println("Doctor inicial creado.");
        }
    }
}