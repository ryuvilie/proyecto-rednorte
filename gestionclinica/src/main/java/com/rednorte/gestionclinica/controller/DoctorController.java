package com.rednorte.gestionclinica.controller;

import com.rednorte.gestionclinica.model.Doctor;
import com.rednorte.gestionclinica.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctores")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "http://32.197.111.18:5173"
})
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> listarActivos() {
        return doctorService.listarActivos();
    }
}