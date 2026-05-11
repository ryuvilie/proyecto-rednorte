package com.rednorte.gestionclinica.service;

import com.rednorte.gestionclinica.model.Doctor;
import com.rednorte.gestionclinica.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> listarActivos() {
        return doctorRepository.findByActivoTrue();
    }
}