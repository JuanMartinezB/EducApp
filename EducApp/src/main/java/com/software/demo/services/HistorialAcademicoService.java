package com.software.demo.services;

import com.software.demo.dtos.HistorialAcademicoRequestDTO;
import com.software.demo.entities.Asignatura;
import com.software.demo.entities.Estudiante;
import com.software.demo.entities.HistorialAcademico;
import com.software.demo.repositories.AsignaturaRepository;
import com.software.demo.repositories.EstudianteRepository;
import com.software.demo.repositories.HistorialAcademicoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistorialAcademicoService {

    @Autowired
    private HistorialAcademicoRepository historialAcademicoRepository;
    
    @Autowired
    private EstudianteRepository estudianteRepository; 
    
    @Autowired
    private AsignaturaRepository asignaturaRepository;

    public List<HistorialAcademico> findAll() {
        return historialAcademicoRepository.findAll();
    }

    public Optional<HistorialAcademico> findById(Long id) {
        return historialAcademicoRepository.findById(id);
    }
    
    // Método general de guardado
    public HistorialAcademico save(HistorialAcademico historialAcademico) {
        return historialAcademicoRepository.save(historialAcademico);
    }

    public void deleteById(Long id) {
        historialAcademicoRepository.deleteById(id);
    }
    
    // 🚨 Nuevo: Guarda historial a partir del DTO de Request (POST) 🚨
    @Transactional
    public HistorialAcademico saveFromDTO(HistorialAcademicoRequestDTO request) {
        Estudiante estudiante = estudianteRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado con ID: " + request.getEstudianteId()));

        Asignatura asignatura = asignaturaRepository.findById(request.getAsignaturaId())
                .orElseThrow(() -> new EntityNotFoundException("Asignatura no encontrada con ID: " + request.getAsignaturaId()));

        HistorialAcademico historial = new HistorialAcademico();
        historial.setEstudiante(estudiante);
        historial.setAsignatura(asignatura);
        historial.setNota(request.getNota());
        historial.setSemestre(request.getSemestre());
        
        return historialAcademicoRepository.save(historial);
    }

    // 🚨 Nuevo: Actualiza historial a partir del DTO de Request (PUT) 🚨
    @Transactional
    public HistorialAcademico updateFromDTO(Long id, HistorialAcademicoRequestDTO request) {
        HistorialAcademico existingHistorial = historialAcademicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Registro de Historial no encontrado con ID: " + id));

        // Revalidar y actualizar Estudiante y Asignatura si cambiaron
        if (!existingHistorial.getEstudiante().getId().equals(request.getEstudianteId())) {
             Estudiante estudiante = estudianteRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado con ID: " + request.getEstudianteId()));
             existingHistorial.setEstudiante(estudiante);
        }

        if (!existingHistorial.getAsignatura().getId().equals(request.getAsignaturaId())) {
             Asignatura asignatura = asignaturaRepository.findById(request.getAsignaturaId())
                .orElseThrow(() -> new EntityNotFoundException("Asignatura no encontrada con ID: " + request.getAsignaturaId()));
             existingHistorial.setAsignatura(asignatura);
        }
        
        // Actualizar campos
        existingHistorial.setNota(request.getNota());
        existingHistorial.setSemestre(request.getSemestre());
        
        return historialAcademicoRepository.save(existingHistorial);
    }

    public boolean hasApproved(Long estudianteId, Long id, double notaMinimaPrerreq) {
        Optional<HistorialAcademico> historialOpt = historialAcademicoRepository
                .findByEstudianteIdAndAsignaturaId(estudianteId, id);
        return historialOpt.isPresent() && historialOpt.get().getNota() >= notaMinimaPrerreq;
    }
    
}