package com.rednorte.gestionclinica.repository;

import com.rednorte.gestionclinica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    boolean existsByRut(String rut);
}