package com.rednorte.gestionclinica.controller;

import com.rednorte.gestionclinica.model.EstadoUsuario;
import com.rednorte.gestionclinica.model.RolUsuario;
import com.rednorte.gestionclinica.model.Usuario;
import com.rednorte.gestionclinica.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @PutMapping("/{id}/rol")
    public Usuario cambiarRol(@PathVariable Long id, @RequestBody Map<String, String> body) {
        RolUsuario nuevoRol = RolUsuario.valueOf(body.get("rol"));
        return usuarioService.cambiarRol(id, nuevoRol);
    }

    @PutMapping("/{id}/estado")
    public Usuario cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        EstadoUsuario nuevoEstado = EstadoUsuario.valueOf(body.get("estado"));
        return usuarioService.cambiarEstado(id, nuevoEstado);
    }

    @PutMapping("/{id}/permisos")
    public Usuario actualizarPermisos(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        return usuarioService.actualizarPermisos(
                id,
                body.get("puedeGestionarUsuarios"),
                body.get("puedeGestionarPacientes"),
                body.get("puedeGestionarCitas"),
                body.get("puedeGestionarListaEspera"),
                body.get("puedeGestionarReportes")
        );
    }
}