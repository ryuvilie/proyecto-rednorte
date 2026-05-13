package com.rednorte.bff.controller;

import com.rednorte.bff.service.BffService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bff")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "http://32.197.111.18:5173"
})
public class BffController {

    private final BffService bffService;

    public BffController(BffService bffService) {
        this.bffService = bffService;
    }

    // =========================
    // AUTH
    // =========================

    @PostMapping("/auth/login")
    public Object login(@RequestBody Object loginRequest) {
        return bffService.login(loginRequest);
    }

    @PostMapping("/auth/register")
    public Object register(@RequestBody Object registerRequest) {
        return bffService.register(registerRequest);
    }

    // =========================
    // USUARIOS
    // =========================

    @GetMapping("/usuarios")
    public Object obtenerUsuarios() {
        return bffService.obtenerUsuarios();
    }

    @PutMapping("/usuarios/{id}/rol")
    public Object cambiarRolUsuario(@PathVariable Long id, @RequestBody Object body) {
        return bffService.cambiarRolUsuario(id, body);
    }

    @PutMapping("/usuarios/{id}/estado")
    public Object cambiarEstadoUsuario(@PathVariable Long id, @RequestBody Object body) {
        return bffService.cambiarEstadoUsuario(id, body);
    }

    @PutMapping("/usuarios/{id}/permisos")
    public Object actualizarPermisosUsuario(@PathVariable Long id, @RequestBody Object body) {
        return bffService.actualizarPermisosUsuario(id, body);
    }

    // =========================
    // PACIENTES
    // =========================

    @GetMapping("/pacientes")
    public Object obtenerPacientes() {
        return bffService.obtenerPacientes();
    }

    @PostMapping("/pacientes")
    public Object crearPaciente(@RequestBody Object paciente) {
        return bffService.crearPaciente(paciente);
    }

    @DeleteMapping("/pacientes/{id}")
    public void eliminarPaciente(@PathVariable Long id) {
        bffService.eliminarPaciente(id);
    }

    @DeleteMapping("/pacientes/{id}/dependencias")
    public void limpiarDependenciasPaciente(@PathVariable Long id) {
        bffService.limpiarDependenciasPaciente(id);
    }

    // =========================
    // DOCTORES
    // =========================

    @GetMapping("/doctores")
    public Object obtenerDoctores() {
        return bffService.obtenerDoctores();
    }

    // =========================
    // LISTA ESPERA
    // =========================

    @GetMapping("/lista-espera")
    public Object obtenerListaEspera() {
        return bffService.obtenerListaEspera();
    }

    @PostMapping("/lista-espera")
    public Object crearListaEspera(@RequestBody Object listaEspera) {
        return bffService.crearListaEspera(listaEspera);
    }

    // =========================
    // CITAS
    // =========================

    @GetMapping("/citas")
    public Object obtenerCitas() {
        return bffService.obtenerCitas();
    }

    @PostMapping("/citas")
    public Object crearCita(@RequestBody Object cita) {
        return bffService.crearCita(cita);
    }

    @GetMapping("/citas/disponibles")
    public Object obtenerCitasDisponibles() {
        return bffService.obtenerCitasDisponibles();
    }

    @GetMapping("/citas/doctor/{doctorId}")
    public Object obtenerCitasDoctor(@PathVariable Long doctorId) {
        return bffService.obtenerCitasDoctor(doctorId);
    }

    @PutMapping("/citas/{citaId}/reservar/{listaEsperaId}")
    public Object reservarCita(
            @PathVariable Long citaId,
            @PathVariable Long listaEsperaId
    ) {
        return bffService.reservarCita(citaId, listaEsperaId);
    }

    @PutMapping("/citas/{citaId}/reservar-paciente/{pacienteId}")
    public Object reservarCitaPaciente(
            @PathVariable Long citaId,
            @PathVariable Long pacienteId
    ) {
        return bffService.reservarCitaPaciente(citaId, pacienteId);
    }

    @DeleteMapping("/citas/{id}")
    public void eliminarCita(@PathVariable Long id) {
        bffService.eliminarCita(id);
    }

    // =========================
    // SOPORTE
    // =========================

    @GetMapping("/notificaciones")
    public Object obtenerNotificaciones() {
        return bffService.obtenerNotificaciones();
    }

    @GetMapping("/reasignaciones")
    public Object obtenerReasignaciones() {
        return bffService.obtenerReasignaciones();
    }

    @GetMapping("/reportes")
    public Object obtenerReportes() {
        return bffService.obtenerReportes();
    }
}