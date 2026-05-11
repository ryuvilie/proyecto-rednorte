package com.rednorte.gestionclinica.repository;

import com.rednorte.gestionclinica.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByActivoTrue();

    Optional<Doctor> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
}