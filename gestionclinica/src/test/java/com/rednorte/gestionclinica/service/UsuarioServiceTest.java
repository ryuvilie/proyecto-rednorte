package com.rednorte.gestionclinica.service;

import com.rednorte.gestionclinica.model.EstadoUsuario;
import com.rednorte.gestionclinica.model.RolUsuario;
import com.rednorte.gestionclinica.model.Usuario;
import com.rednorte.gestionclinica.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    void listarUsuariosDebeRetornarUsuarios() {
        when(usuarioRepository.findAll())
                .thenReturn(new ArrayList<>());

        assertNotNull(usuarioService.listarUsuarios());

        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void cambiarRolAAdminDebeAsignarPermisos() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setRol(RolUsuario.PACIENTE);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.cambiarRol(1L, RolUsuario.ADMIN_CLINICA);

        assertEquals(RolUsuario.ADMIN_CLINICA, resultado.getRol());
        assertTrue(resultado.getPuedeGestionarUsuarios());
        assertTrue(resultado.getPuedeGestionarPacientes());
        assertTrue(resultado.getPuedeGestionarCitas());
        assertTrue(resultado.getPuedeGestionarListaEspera());
        assertTrue(resultado.getPuedeGestionarReportes());

        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void cambiarRolAPacienteDebeQuitarPermisos() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setRol(RolUsuario.ADMIN_CLINICA);
        usuario.setPuedeGestionarUsuarios(true);
        usuario.setPuedeGestionarPacientes(true);
        usuario.setPuedeGestionarCitas(true);
        usuario.setPuedeGestionarListaEspera(true);
        usuario.setPuedeGestionarReportes(true);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.cambiarRol(1L, RolUsuario.PACIENTE);

        assertEquals(RolUsuario.PACIENTE, resultado.getRol());
        assertFalse(resultado.getPuedeGestionarUsuarios());
        assertFalse(resultado.getPuedeGestionarPacientes());
        assertFalse(resultado.getPuedeGestionarCitas());
        assertFalse(resultado.getPuedeGestionarListaEspera());
        assertFalse(resultado.getPuedeGestionarReportes());

        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void cambiarRolDebeLanzarErrorSiUsuarioNoExiste() {
        when(usuarioRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.cambiarRol(99L, RolUsuario.ADMIN_CLINICA);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void cambiarEstadoDebeActualizarEstado() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEstado(EstadoUsuario.ACTIVO);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.cambiarEstado(1L, EstadoUsuario.INACTIVO);

        assertEquals(EstadoUsuario.INACTIVO, resultado.getEstado());

        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void actualizarPermisosDebeActualizarTodosLosPermisos() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Usuario resultado = usuarioService.actualizarPermisos(
                1L,
                true,
                false,
                true,
                false,
                true
        );

        assertTrue(resultado.getPuedeGestionarUsuarios());
        assertFalse(resultado.getPuedeGestionarPacientes());
        assertTrue(resultado.getPuedeGestionarCitas());
        assertFalse(resultado.getPuedeGestionarListaEspera());
        assertTrue(resultado.getPuedeGestionarReportes());

        verify(usuarioRepository, times(1)).save(usuario);
    }
}