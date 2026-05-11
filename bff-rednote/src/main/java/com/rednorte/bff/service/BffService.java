package com.rednorte.bff.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BffService {

    private final RestTemplate restTemplate;

    @Value("${gestion.clinica.url}")
    private String gestionClinicaUrl;

    @Value("${gestion.soporte.url}")
    private String gestionSoporteUrl;

    public BffService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // =========================
    // AUTH
    // =========================

    public Object login(Object loginRequest) {
        return restTemplate.postForObject(
                gestionClinicaUrl + "/auth/login",
                loginRequest,
                Object.class
        );
    }

    public Object register(Object registerRequest) {
        return restTemplate.postForObject(
                gestionClinicaUrl + "/auth/register",
                registerRequest,
                Object.class
        );
    }

    // =========================
    // USUARIOS
    // =========================

    public Object obtenerUsuarios() {
        return restTemplate.getForObject(
                gestionClinicaUrl + "/usuarios",
                Object.class
        );
    }

    public Object cambiarRolUsuario(Long id, Object body) {
        return ejecutarPutConRespuesta(
                gestionClinicaUrl + "/usuarios/" + id + "/rol",
                body
        );
    }

    public Object cambiarEstadoUsuario(Long id, Object body) {
        return ejecutarPutConRespuesta(
                gestionClinicaUrl + "/usuarios/" + id + "/estado",
                body
        );
    }

    public Object actualizarPermisosUsuario(Long id, Object body) {
        return ejecutarPutConRespuesta(
                gestionClinicaUrl + "/usuarios/" + id + "/permisos",
                body
        );
    }

    private Object ejecutarPutConRespuesta(String url, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                Object.class
        );

        return response.getBody();
    }

    // =========================
    // PACIENTES
    // =========================

    public Object obtenerPacientes() {
        return restTemplate.getForObject(
                gestionClinicaUrl + "/pacientes",
                Object.class
        );
    }

    public Object crearPaciente(Object paciente) {
        return restTemplate.postForObject(
                gestionClinicaUrl + "/pacientes",
                paciente,
                Object.class
        );
    }

    public void eliminarPaciente(Long id) {
        restTemplate.delete(gestionClinicaUrl + "/pacientes/" + id);
    }

    public void limpiarDependenciasPaciente(Long id) {
        restTemplate.delete(gestionClinicaUrl + "/pacientes/" + id + "/dependencias");
    }

    // =========================
    // DOCTORES
    // =========================

    public Object obtenerDoctores() {
        return restTemplate.getForObject(
                gestionClinicaUrl + "/doctores",
                Object.class
        );
    }

    // =========================
    // LISTA DE ESPERA
    // =========================

    public Object obtenerListaEspera() {
        return restTemplate.getForObject(
                gestionClinicaUrl + "/lista-espera",
                Object.class
        );
    }

    public Object crearListaEspera(Object listaEspera) {
        return restTemplate.postForObject(
                gestionClinicaUrl + "/lista-espera",
                listaEspera,
                Object.class
        );
    }

    // =========================
    // CITAS
    // =========================

    public Object obtenerCitas() {
        return restTemplate.getForObject(
                gestionClinicaUrl + "/citas",
                Object.class
        );
    }

    public Object crearCita(Object cita) {
        return restTemplate.postForObject(
                gestionClinicaUrl + "/citas",
                cita,
                Object.class
        );
    }

    public Object obtenerCitasDisponibles() {
        return restTemplate.getForObject(
                gestionClinicaUrl + "/citas/disponibles",
                Object.class
        );
    }

    public Object obtenerCitasDoctor(Long doctorId) {
        return restTemplate.getForObject(
                gestionClinicaUrl + "/citas/doctor/" + doctorId,
                Object.class
        );
    }

    public Object reservarCita(Long citaId, Long listaEsperaId) {
        return ejecutarPutConRespuesta(
                gestionClinicaUrl + "/citas/" + citaId + "/reservar/" + listaEsperaId,
                null
        );
    }

    public Object reservarCitaPaciente(Long citaId, Long pacienteId) {
        return ejecutarPutConRespuesta(
            gestionClinicaUrl + "/citas/" + citaId + "/reservar-paciente/" + pacienteId,
            null
        );
    }

    // =========================
    // SOPORTE
    // =========================

    public Object obtenerNotificaciones() {
        return restTemplate.getForObject(
                gestionSoporteUrl + "/notificaciones",
                Object.class
        );
    }

    public Object obtenerReasignaciones() {
        return restTemplate.getForObject(
                gestionSoporteUrl + "/reasignaciones",
                Object.class
        );
    }

    public Object obtenerReportes() {
        return restTemplate.getForObject(
                gestionSoporteUrl + "/reportes",
                Object.class
        );
    }
}