package com.software.demo.controllers;

import com.software.demo.entities.HistorialAcademico;
import com.software.demo.services.HistorialAcademicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historial-academico")
public class HistorialAcademicoController {

    @Autowired
    private HistorialAcademicoService historialAcademicoService;

    @GetMapping
    public List<HistorialAcademico> getAllHistorialAcademico() {
        return historialAcademicoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialAcademico> getHistorialAcademicoById(@PathVariable Long id) {
        Optional<HistorialAcademico> historialAcademico = historialAcademicoService.findById(id);
        return historialAcademico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public HistorialAcademico createHistorialAcademico(@RequestBody HistorialAcademico historialAcademico) {
        return historialAcademicoService.save(historialAcademico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialAcademico> updateHistorialAcademico(@PathVariable Long id, @RequestBody HistorialAcademico historialAcademicoDetails) {
        Optional<HistorialAcademico> historialAcademico = historialAcademicoService.findById(id);
        if (historialAcademico.isPresent()) {
            historialAcademicoDetails.setId(id);
            return ResponseEntity.ok(historialAcademicoService.save(historialAcademicoDetails));
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