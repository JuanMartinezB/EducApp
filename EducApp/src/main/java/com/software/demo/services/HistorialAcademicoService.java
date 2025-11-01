package com.software.demo.services;

import com.software.demo.entities.HistorialAcademico;
import com.software.demo.repositories.HistorialAcademicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialAcademicoService {

    @Autowired
    private HistorialAcademicoRepository historialAcademicoRepository;

    public List<HistorialAcademico> findAll() {
        return historialAcademicoRepository.findAll();
    }

    public Optional<HistorialAcademico> findById(Long id) {
        return historialAcademicoRepository.findById(id);
    }

    public HistorialAcademico save(HistorialAcademico historialAcademico) {
        return historialAcademicoRepository.save(historialAcademico);
    }

    public void deleteById(Long id) {
        historialAcademicoRepository.deleteById(id);
    }

}