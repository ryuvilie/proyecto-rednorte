package com.rednorte.gestionclinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;

@Entity
@Table(name = "cita")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FutureOrPresent(message = "La fecha de la cita no puede ser pasada")
    @Column(nullable = false)
    private LocalDate fechaCita;

    @Column(nullable = false)
    private LocalTime horaCita;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String establecimiento;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String estadoCita;

    @ManyToOne(optional = true)
    @JoinColumn(name = "lista_espera_id", nullable = true)
    private ListaEspera listaEspera;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}