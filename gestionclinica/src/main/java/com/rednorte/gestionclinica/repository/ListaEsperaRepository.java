package com.rednorte.gestionclinica.repository;

import com.rednorte.gestionclinica.model.ListaEspera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListaEsperaRepository extends JpaRepository<ListaEspera, Long> {

    List<ListaEspera> findByPacienteId(Long pacienteId);
}