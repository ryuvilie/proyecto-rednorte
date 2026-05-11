package com.rednorte.gestionsoporte.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reasignacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reasignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaReasignacion;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String motivo;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String estado;

    private Long idCita;
}