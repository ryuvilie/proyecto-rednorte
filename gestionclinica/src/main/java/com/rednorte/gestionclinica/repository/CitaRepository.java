package com.rednorte.gestionclinica.repository;

import com.rednorte.gestionclinica.model.Cita;
import com.rednorte.gestionclinica.model.Doctor;
import com.rednorte.gestionclinica.model.ListaEspera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByListaEspera(ListaEspera listaEspera);

    List<Cita> findByDoctor(Doctor doctor);

    List<Cita> findByListaEsperaIsNull();

    List<Cita> findByEstadoCita(String estadoCita);
}