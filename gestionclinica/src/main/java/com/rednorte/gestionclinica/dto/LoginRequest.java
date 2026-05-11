package com.rednorte.gestionclinica.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String correo;
    private String password;
}