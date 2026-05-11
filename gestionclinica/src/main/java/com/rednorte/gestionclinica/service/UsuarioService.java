package com.rednorte.gestionclinica.service;

import com.rednorte.gestionclinica.model.EstadoUsuario;
import com.rednorte.gestionclinica.model.RolUsuario;
import com.rednorte.gestionclinica.model.Usuario;
import com.rednorte.gestionclinica.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario cambiarRol(Long id, RolUsuario nuevoRol) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setRol(nuevoRol);

        if (nuevoRol == RolUsuario.ADMIN_CLINICA) {
            usuario.setPuedeGestionarUsuarios(true);
            usuario.setPuedeGestionarPacientes(true);
            usuario.setPuedeGestionarCitas(true);
            usuario.setPuedeGestionarListaEspera(true);
            usuario.setPuedeGestionarReportes(true);
        }

        if (nuevoRol == RolUsuario.PACIENTE) {
            usuario.setPuedeGestionarUsuarios(false);
            usuario.setPuedeGestionarPacientes(false);
            usuario.setPuedeGestionarCitas(false);
            usuario.setPuedeGestionarListaEspera(false);
            usuario.setPuedeGestionarReportes(false);
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario cambiarEstado(Long id, EstadoUsuario nuevoEstado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEstado(nuevoEstado);

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarPermisos(
            Long id,
            Boolean puedeGestionarUsuarios,
            Boolean puedeGestionarPacientes,
            Boolean puedeGestionarCitas,
            Boolean puedeGestionarListaEspera,
            Boolean puedeGestionarReportes
    ) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setPuedeGestionarUsuarios(puedeGestionarUsuarios);
        usuario.setPuedeGestionarPacientes(puedeGestionarPacientes);
        usuario.setPuedeGestionarCitas(puedeGestionarCitas);
        usuario.setPuedeGestionarListaEspera(puedeGestionarListaEspera);
        usuario.setPuedeGestionarReportes(puedeGestionarReportes);

        return usuarioRepository.save(usuario);
    }
}