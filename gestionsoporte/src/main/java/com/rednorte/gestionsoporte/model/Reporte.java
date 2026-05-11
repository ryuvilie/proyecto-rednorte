package com.rednorte.gestionsoporte.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaReporte;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String tipoReporte;

    private Integer totalSolicitudes;
    private Integer totalCitas;
    private Integer tiempoPromedioEspera;
}