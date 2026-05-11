package com.rednorte.gestionclinica.service;

import com.rednorte.gestionclinica.model.Cita;
import com.rednorte.gestionclinica.model.ListaEspera;
import com.rednorte.gestionclinica.model.Paciente;
import com.rednorte.gestionclinica.repository.CitaRepository;
import com.rednorte.gestionclinica.repository.ListaEsperaRepository;
import com.rednorte.gestionclinica.repository.PacienteRepository;
import com.rednorte.gestionclinica.repository.UsuarioRepository;
import com.rednorte.gestionclinica.util.RutUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final ListaEsperaRepository listaEsperaRepository;
    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;

    public PacienteService(
            PacienteRepository pacienteRepository,
            ListaEsperaRepository listaEsperaRepository,
            CitaRepository citaRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.pacienteRepository = pacienteRepository;
        this.listaEsperaRepository = listaEsperaRepository;
        this.citaRepository = citaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Paciente> listar() {
        return pacienteRepository.findAll();
    }

    public Paciente guardar(Paciente paciente) {

        if (!RutUtil.validarRut(paciente.getRut())) {
            throw new RuntimeException("El RUT ingresado no es válido");
        }

        if (pacienteRepository.existsByRut(paciente.getRut())) {
            throw new RuntimeException("Ya existe un paciente registrado con ese RUT");
        }

        return pacienteRepository.save(paciente);
    }

    public void limpiarDependencias(Long pacienteId) {

        List<ListaEspera> listas = listaEsperaRepository.findByPacienteId(pacienteId);

        for (ListaEspera lista : listas) {
            List<Cita> citas = citaRepository.findByListaEspera(lista);
            citaRepository.deleteAll(citas);
        }

        listaEsperaRepository.deleteAll(listas);
    }

    public void eliminar(Long id) {

        limpiarDependencias(id);

        usuarioRepository.findByPacienteId(id).ifPresent(usuario -> {
            usuario.setPaciente(null);
            usuarioRepository.save(usuario);
        });

        pacienteRepository.deleteById(id);
    }
}