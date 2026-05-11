package com.rednorte.gestionsoporte.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idPaciente;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String tipo;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String mensaje;

    private LocalDateTime fechaEnvio;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String estadoEnvio;
}