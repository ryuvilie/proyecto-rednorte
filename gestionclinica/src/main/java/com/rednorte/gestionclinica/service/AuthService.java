package com.rednorte.gestionclinica.service;

import com.rednorte.gestionclinica.dto.AuthResponse;
import com.rednorte.gestionclinica.dto.LoginRequest;
import com.rednorte.gestionclinica.dto.RegisterRequest;
import com.rednorte.gestionclinica.model.EstadoUsuario;
import com.rednorte.gestionclinica.model.Paciente;
import com.rednorte.gestionclinica.model.RolUsuario;
import com.rednorte.gestionclinica.model.Usuario;
import com.rednorte.gestionclinica.repository.PacienteRepository;
import com.rednorte.gestionclinica.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(
            UsuarioRepository usuarioRepository,
            PacienteRepository pacienteRepository,
            JwtService jwtService
    ) {
        this.usuarioRepository = usuarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            throw new RuntimeException("Usuario inactivo");
        }

        String token = jwtService.generarToken(usuario);

        return construirRespuesta(usuario, token);
    }

    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario registrado con este correo");
        }

        Paciente paciente = new Paciente();
        paciente.setRut(request.getRut());
        paciente.setNombre(request.getNombre());
        paciente.setApellido(request.getApellido());
        paciente.setFechaNacimiento(request.getFechaNacimiento());
        paciente.setTelefono(request.getTelefono());
        paciente.setCorreo(request.getCorreo());

        Paciente pacienteGuardado = pacienteRepository.save(paciente);

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre() + " " + request.getApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(RolUsuario.PACIENTE);
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setPaciente(pacienteGuardado);

        usuario.setPuedeGestionarUsuarios(false);
        usuario.setPuedeGestionarPacientes(false);
        usuario.setPuedeGestionarCitas(false);
        usuario.setPuedeGestionarListaEspera(false);
        usuario.setPuedeGestionarReportes(false);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        String token = jwtService.generarToken(usuarioGuardado);

        return construirRespuesta(usuarioGuardado, token);
    }

    private AuthResponse construirRespuesta(Usuario usuario, String token) {
        Long pacienteId = null;
        Long doctorId = null;

        if (usuario.getPaciente() != null) {
            pacienteId = usuario.getPaciente().getId();
        }

        if (usuario.getDoctor() != null) {
            doctorId = usuario.getDoctor().getId();
        }

        return new AuthResponse(
                usuario.getId(),
                pacienteId,
                doctorId,
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol(),
                usuario.getEstado(),
                usuario.getPuedeGestionarUsuarios(),
                usuario.getPuedeGestionarPacientes(),
                usuario.getPuedeGestionarCitas(),
                usuario.getPuedeGestionarListaEspera(),
                usuario.getPuedeGestionarReportes(),
                token,
                jwtService.getJwtExpirationMs()
        );
    }
}