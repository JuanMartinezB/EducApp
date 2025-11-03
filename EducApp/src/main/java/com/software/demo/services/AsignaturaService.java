package com.software.demo.services;

import com.software.demo.dtos.AsignaturaRequestDTO;
import com.software.demo.entities.Asignatura;
import com.software.demo.repositories.AsignaturaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    
    @Transactional
    public Asignatura saveFromDTO(AsignaturaRequestDTO request) {
        Asignatura asignatura = new Asignatura();
        
        asignatura.setCodigo(request.getCodigo());
        asignatura.setNombre(request.getNombre());
        asignatura.setCreditos(request.getCreditos());
        asignatura.setCupoMaximo(request.getCupoMaximo());
        asignatura.setSemestre(request.getSemestre());
        asignatura.setHorario(request.getHorario());
        asignatura.setCupoActual(0);
        
        if (request.getPrerrequisitosIds() != null && !request.getPrerrequisitosIds().isEmpty()) {
            List<Asignatura> prerrequisitos = new ArrayList<>();
            for (Long id : request.getPrerrequisitosIds()) {
                asignaturaRepository.findById(id).ifPresent(prerrequisitos::add);
            }
            asignatura.setPrerrequisitos(prerrequisitos);
        }
        return asignaturaRepository.save(asignatura);
    }

    @Transactional
    public Asignatura updateFromDTO(Long id, AsignaturaRequestDTO request) {
        Asignatura existingAsignatura = asignaturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Asignatura no encontrada con ID: " + id));

        existingAsignatura.setCodigo(request.getCodigo());
        existingAsignatura.setNombre(request.getNombre());
        existingAsignatura.setCreditos(request.getCreditos());
        existingAsignatura.setCupoMaximo(request.getCupoMaximo());
        existingAsignatura.setSemestre(request.getSemestre());
        existingAsignatura.setHorario(request.getHorario());

        existingAsignatura.getPrerrequisitos().clear();
        if (request.getPrerrequisitosIds() != null && !request.getPrerrequisitosIds().isEmpty()) {
            List<Asignatura> prerrequisitos = new ArrayList<>();
            for (Long reqId : request.getPrerrequisitosIds()) {
                asignaturaRepository.findById(reqId).ifPresent(prerrequisitos::add);
            }
            existingAsignatura.setPrerrequisitos(prerrequisitos);
        }
        return asignaturaRepository.save(existingAsignatura);
    }
}