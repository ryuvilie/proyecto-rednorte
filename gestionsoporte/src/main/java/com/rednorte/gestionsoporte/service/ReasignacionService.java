package com.rednorte.gestionsoporte.service;

import com.rednorte.gestionsoporte.model.Reasignacion;
import com.rednorte.gestionsoporte.repository.ReasignacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReasignacionService {

    private final ReasignacionRepository reasignacionRepository;

    public ReasignacionService(ReasignacionRepository reasignacionRepository) {
        this.reasignacionRepository = reasignacionRepository;
    }

    public List<Reasignacion> listar() {
        return reasignacionRepository.findAll();
    }

    public Reasignacion guardar(Reasignacion reasignacion) {
        return reasignacionRepository.save(reasignacion);
    }
}