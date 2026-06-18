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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private UsuarioRepository usuarioRepository;
    private PacienteRepository pacienteRepository;
    private JwtService jwtService;
    private AuthService authService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        pacienteRepository = mock(PacienteRepository.class);
        jwtService = mock(JwtService.class);

        authService = new AuthService(
                usuarioRepository,
                pacienteRepository,
                jwtService
        );
    }

    @Test
    void loginDebeRetornarAuthResponseCuandoCredencialesSonValidas() {
        LoginRequest request = new LoginRequest();
        request.setCorreo("admin@rednorte.cl");
        request.setPassword("1234");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Administrador Clínica");
        usuario.setCorreo("admin@rednorte.cl");
        usuario.setPassword(passwordEncoder.encode("1234"));
        usuario.setRol(RolUsuario.ADMIN_CLINICA);
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setPuedeGestionarUsuarios(true);
        usuario.setPuedeGestionarPacientes(true);
        usuario.setPuedeGestionarCitas(true);
        usuario.setPuedeGestionarListaEspera(true);
        usuario.setPuedeGestionarReportes(true);

        when(usuarioRepository.findByCorreo("admin@rednorte.cl"))
                .thenReturn(Optional.of(usuario));

        when(jwtService.generarToken(usuario))
                .thenReturn("token-test");

        when(jwtService.getJwtExpirationMs())
                .thenReturn(3600000L);

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("Administrador Clínica", response.getNombre());
        assertEquals("admin@rednorte.cl", response.getCorreo());
        assertEquals(RolUsuario.ADMIN_CLINICA, response.getRol());
        assertEquals(EstadoUsuario.ACTIVO, response.getEstado());
        assertEquals("token-test", response.getToken());

        verify(usuarioRepository, times(1)).findByCorreo("admin@rednorte.cl");
        verify(jwtService, times(1)).generarToken(usuario);
    }

    @Test
    void loginDebeLanzarErrorSiUsuarioNoExiste() {
        LoginRequest request = new LoginRequest();
        request.setCorreo("noexiste@rednorte.cl");
        request.setPassword("1234");

        when(usuarioRepository.findByCorreo("noexiste@rednorte.cl"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(jwtService, never()).generarToken(any(Usuario.class));
    }

    @Test
    void loginDebeLanzarErrorSiPasswordEsIncorrecta() {
        LoginRequest request = new LoginRequest();
        request.setCorreo("admin@rednorte.cl");
        request.setPassword("incorrecta");

        Usuario usuario = new Usuario();
        usuario.setCorreo("admin@rednorte.cl");
        usuario.setPassword(passwordEncoder.encode("1234"));
        usuario.setEstado(EstadoUsuario.ACTIVO);

        when(usuarioRepository.findByCorreo("admin@rednorte.cl"))
                .thenReturn(Optional.of(usuario));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertEquals("Contraseña incorrecta", exception.getMessage());
        verify(jwtService, never()).generarToken(any(Usuario.class));
    }

    @Test
    void loginDebeLanzarErrorSiUsuarioEstaInactivo() {
        LoginRequest request = new LoginRequest();
        request.setCorreo("juan@test.com");
        request.setPassword("1234");

        Usuario usuario = new Usuario();
        usuario.setCorreo("juan@test.com");
        usuario.setPassword(passwordEncoder.encode("1234"));
        usuario.setEstado(EstadoUsuario.INACTIVO);

        when(usuarioRepository.findByCorreo("juan@test.com"))
                .thenReturn(Optional.of(usuario));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertEquals("Usuario inactivo", exception.getMessage());
        verify(jwtService, never()).generarToken(any(Usuario.class));
    }

    @Test
    void registerDebeCrearPacienteUsuarioYRetornarAuthResponse() {
        RegisterRequest request = new RegisterRequest();
        request.setRut("11111111-1");
        request.setNombre("María");
        request.setApellido("González");
        request.setFechaNacimiento(LocalDate.of(1995, 5, 10));
        request.setTelefono("912345678");
        request.setCorreo("maria@test.com");
        request.setPassword("1234");

        Paciente pacienteGuardado = new Paciente();
        pacienteGuardado.setId(10L);
        pacienteGuardado.setRut(request.getRut());
        pacienteGuardado.setNombre(request.getNombre());
        pacienteGuardado.setApellido(request.getApellido());
        pacienteGuardado.setCorreo(request.getCorreo());

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(20L);
        usuarioGuardado.setNombre("María González");
        usuarioGuardado.setCorreo("maria@test.com");
        usuarioGuardado.setRol(RolUsuario.PACIENTE);
        usuarioGuardado.setEstado(EstadoUsuario.ACTIVO);
        usuarioGuardado.setPaciente(pacienteGuardado);
        usuarioGuardado.setPuedeGestionarUsuarios(false);
        usuarioGuardado.setPuedeGestionarPacientes(false);
        usuarioGuardado.setPuedeGestionarCitas(false);
        usuarioGuardado.setPuedeGestionarListaEspera(false);
        usuarioGuardado.setPuedeGestionarReportes(false);

        when(usuarioRepository.existsByCorreo("maria@test.com"))
                .thenReturn(false);

        when(pacienteRepository.save(any(Paciente.class)))
                .thenReturn(pacienteGuardado);

        when(usuarioRepository.save(any(Usuario.class)))
                .thenReturn(usuarioGuardado);

        when(jwtService.generarToken(usuarioGuardado))
                .thenReturn("token-registro");

        when(jwtService.getJwtExpirationMs())
                .thenReturn(3600000L);

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals(20L, response.getUsuarioId());
        assertEquals(10L, response.getPacienteId());
        assertEquals("María González", response.getNombre());
        assertEquals("maria@test.com", response.getCorreo());
        assertEquals(RolUsuario.PACIENTE, response.getRol());
        assertEquals("token-registro", response.getToken());

        verify(pacienteRepository, times(1)).save(any(Paciente.class));
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        verify(jwtService, times(1)).generarToken(usuarioGuardado);
    }

    @Test
    void registerDebeLanzarErrorSiCorreoYaExiste() {
        RegisterRequest request = new RegisterRequest();
        request.setCorreo("repetido@test.com");

        when(usuarioRepository.existsByCorreo("repetido@test.com"))
                .thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.register(request);
        });

        assertEquals("Ya existe un usuario registrado con este correo", exception.getMessage());

        verify(pacienteRepository, never()).save(any(Paciente.class));
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}