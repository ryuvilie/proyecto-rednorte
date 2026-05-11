package com.rednorte.gestionclinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String nombre;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true, length = 120)
    private String correo;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RolUsuario rol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoUsuario estado;

    @Column(nullable = false)
    private Boolean puedeGestionarUsuarios = false;

    @Column(nullable = false)
    private Boolean puedeGestionarPacientes = false;

    @Column(nullable = false)
    private Boolean puedeGestionarCitas = false;

    @Column(nullable = false)
    private Boolean puedeGestionarListaEspera = false;

    @Column(nullable = false)
    private Boolean puedeGestionarReportes = false;

    @OneToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @OneToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}