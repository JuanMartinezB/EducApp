package com.software.demo.controllers;

import com.software.demo.entities.Inscripcion;
import com.software.demo.services.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @GetMapping
    public List<Inscripcion> getAllInscripciones() {
        return inscripcionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> getInscripcionById(@PathVariable Long id) {
        Optional<Inscripcion> inscripcion = inscripcionService.findById(id);
        return inscripcion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Inscripcion createInscripcion(@RequestBody Inscripcion inscripcion) {
        return inscripcionService.save(inscripcion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> updateInscripcion(@PathVariable Long id, @RequestBody Inscripcion inscripcionDetails) {
        Optional<Inscripcion> inscripcion = inscripcionService.findById(id);
        if (inscripcion.isPresent()) {
            inscripcionDetails.setId(id);
            return ResponseEntity.ok(inscripcionService.save(inscripcionDetails));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInscripcion(@PathVariable Long id) {
        if (inscripcionService.findById(id).isPresent()) {
            inscripcionService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}