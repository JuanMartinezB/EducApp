package com.software.demo.controllers;

import com.software.demo.dtos.InscripcionDTO;
import com.software.demo.dtos.InscripcionRequestDTO;
import com.software.demo.entities.Inscripcion;
import com.software.demo.mappers.InscripcionMapper;
import com.software.demo.services.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inscripciones")
@CrossOrigin(origins = "http://localhost:4200")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;
    
    @GetMapping
    public List<InscripcionDTO> getAllInscripciones() {
        return inscripcionService.findAll().stream()
            .map(InscripcionMapper::toInscripcionDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionDTO> getInscripcionById(@PathVariable Long id) {
        return inscripcionService.findById(id)
                .map(InscripcionMapper::toInscripcionDTO) 
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/inscribir")
    public ResponseEntity<InscripcionDTO> inscribir(@RequestBody InscripcionRequestDTO request) {
        Inscripcion ins = inscripcionService.inscribirAsignatura(
            request.getEstudianteId(),
            request.getAsignaturaId(),
            request.getOperador() != null ? request.getOperador() : "system"
        );
        
        return ResponseEntity.ok(InscripcionMapper.toInscripcionDTO(ins));
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id, @RequestParam(required = false, defaultValue = "system") String operador) {
        inscripcionService.cancelarInscripcion(id, operador);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/activas")
    public List<InscripcionDTO> getInscripcionesActivas(@RequestParam Long estudianteId) {
        List<Inscripcion> activas = inscripcionService.findInscripcionesActivas(estudianteId);
        return activas.stream()
            .map(InscripcionMapper::toInscripcionDTO)
            .collect(Collectors.toList());
    }
}