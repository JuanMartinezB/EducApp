package com.software.demo.services;

import com.software.demo.entities.Inscripcion;
import com.software.demo.repositories.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    public List<Inscripcion> findAll() {
        return inscripcionRepository.findAll();
    }

    public Optional<Inscripcion> findById(Long id) {
        return inscripcionRepository.findById(id);
    }

    public Inscripcion save(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }

    public void deleteById(Long id) {
        inscripcionRepository.deleteById(id);
    }

}