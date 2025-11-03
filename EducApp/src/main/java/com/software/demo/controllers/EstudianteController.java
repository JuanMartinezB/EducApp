package com.software.demo.controllers;

import com.software.demo.dtos.EstudianteListadoDTO;
import com.software.demo.entities.Estudiante;
import com.software.demo.mappers.EstudianteMapper;
import com.software.demo.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "http://localhost:4200")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public List<EstudianteListadoDTO> getAllEstudiantes() {
        return estudianteService.findAll().stream()
                .map(EstudianteMapper::toEstudianteListadoDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteListadoDTO> getEstudianteById(@PathVariable Long id) {
        
        Optional<Estudiante> estudianteOptional = estudianteService.findById(id); 

        if (estudianteOptional.isEmpty()) { 
            return ResponseEntity.notFound().build(); 
        }

        Estudiante estudiante = estudianteOptional.get();
        EstudianteListadoDTO dto = EstudianteMapper.toEstudianteListadoDTO(estudiante); 
        return ResponseEntity.ok(dto);
    }
    
    @PostMapping
    public Estudiante createEstudiante(@RequestBody Estudiante estudiante) {
        return estudianteService.save(estudiante);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> updateEstudiante(@PathVariable Long id, @RequestBody Estudiante estudianteDetails) {
        Optional<Estudiante> estudiante = estudianteService.findById(id);
        if (estudiante.isPresent()) {
            estudianteDetails.setId(id);
            return ResponseEntity.ok(estudianteService.save(estudianteDetails));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstudiante(@PathVariable Long id) {
        if (estudianteService.findById(id).isPresent()) {
            estudianteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}