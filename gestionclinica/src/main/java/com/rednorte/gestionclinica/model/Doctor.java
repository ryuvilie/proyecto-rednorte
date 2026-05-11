package com.rednorte.gestionclinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String apellido;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String especialidad;

    @Email
    @Column(unique = true, length = 120)
    private String correo;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false)
    private Boolean activo = true;
}