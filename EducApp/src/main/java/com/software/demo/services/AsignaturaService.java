package com.software.demo.services;

import com.software.demo.entities.Asignatura;
import com.software.demo.repositories.AsignaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignaturaService {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    public List<Asignatura> findAll() {
        return asignaturaRepository.findAll();
    }

    public Optional<Asignatura> findById(Long id) {
        return asignaturaRepository.findById(id);
    }

    public Asignatura save(Asignatura asignatura) {
        return asignaturaRepository.save(asignatura);
    }

    public void deleteById(Long id) {
        asignaturaRepository.deleteById(id);
    }

}