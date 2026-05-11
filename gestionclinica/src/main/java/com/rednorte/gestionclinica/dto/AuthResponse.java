package com.rednorte.gestionclinica.dto;

import com.rednorte.gestionclinica.model.EstadoUsuario;
import com.rednorte.gestionclinica.model.RolUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Long usuarioId;
    private Long pacienteId;
    private Long doctorId;
    private String nombre;
    private String correo;
    private RolUsuario rol;
    private EstadoUsuario estado;
    private Boolean puedeGestionarUsuarios;
    private Boolean puedeGestionarPacientes;
    private Boolean puedeGestionarCitas;
    private Boolean puedeGestionarListaEspera;
    private Boolean puedeGestionarReportes;
    private String token;
    private Long expiracionMs;
}