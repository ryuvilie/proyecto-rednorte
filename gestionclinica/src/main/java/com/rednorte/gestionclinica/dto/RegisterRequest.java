package com.rednorte.gestionclinica.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String rut;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String telefono;
    private String correo;
    private String password;
}