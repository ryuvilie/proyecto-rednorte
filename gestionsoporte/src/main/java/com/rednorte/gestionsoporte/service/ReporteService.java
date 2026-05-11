package com.rednorte.gestionsoporte.service;

import com.rednorte.gestionsoporte.model.Reporte;
import com.rednorte.gestionsoporte.repository.ReporteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;

    public ReporteService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    public List<Reporte> listar() {
        return reporteRepository.findAll();
    }

    public Reporte guardar(Reporte reporte) {
        return reporteRepository.save(reporte);
    }
}