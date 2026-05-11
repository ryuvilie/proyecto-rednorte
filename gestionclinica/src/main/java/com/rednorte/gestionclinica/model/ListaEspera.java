package com.rednorte.gestionclinica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "lista_espera")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaEspera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La especialidad es obligatoria")
    @Column(nullable = false, length = 100)
    private String especialidad;

    @NotBlank(message = "La prioridad es obligatoria")
    @Column(nullable = false, length = 30)
    private String prioridad;

    @NotBlank(message = "El estado es obligatorio")
    @Column(nullable = false, length = 30)
    private String estado;

    @Column(nullable = false)
    private LocalDate fechaIngreso;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
}