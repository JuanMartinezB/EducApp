package com.software.demo.controllers;

import com.software.demo.dtos.HistorialAcademicoDTO;
import com.software.demo.dtos.HistorialAcademicoRequestDTO;
import com.software.demo.entities.HistorialAcademico;
import com.software.demo.mappers.HistorialAcademicoMapper;
import com.software.demo.services.HistorialAcademicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/historial-academico")
@CrossOrigin(origins = "http://localhost:4200")
public class HistorialAcademicoController {

    @Autowired
    private HistorialAcademicoService historialAcademicoService;

    @GetMapping
    public List<HistorialAcademicoDTO> getAllHistorialAcademico() {
        return historialAcademicoService.findAll().stream()
                .map(HistorialAcademicoMapper::toHistorialAcademicoDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialAcademicoDTO> getHistorialAcademicoById(@PathVariable Long id) {
        return historialAcademicoService.findById(id)
                .map(HistorialAcademicoMapper::toHistorialAcademicoDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public HistorialAcademicoDTO createHistorialAcademico(@RequestBody HistorialAcademicoRequestDTO historialRequest) {
        HistorialAcademico nuevoHistorial = historialAcademicoService.saveFromDTO(historialRequest);
        return HistorialAcademicoMapper.toHistorialAcademicoDTO(nuevoHistorial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialAcademicoDTO> updateHistorialAcademico(@PathVariable Long id, @RequestBody HistorialAcademicoRequestDTO historialDetails) {
        Optional<HistorialAcademico> historialAcademico = historialAcademicoService.findById(id);
        if (historialAcademico.isPresent()) {
            HistorialAcademico updatedHistorial = historialAcademicoService.updateFromDTO(id, historialDetails);
            return ResponseEntity.ok(HistorialAcademicoMapper.toHistorialAcademicoDTO(updatedHistorial));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorialAcademico(@PathVariable Long id) {
        if (historialAcademicoService.findById(id).isPresent()) {
            historialAcademicoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}