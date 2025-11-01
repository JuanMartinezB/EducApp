package com.software.demo.controllers;

import com.software.demo.entities.Asignatura;
import com.software.demo.services.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    @GetMapping
    public List<Asignatura> getAllAsignaturas() {
        return asignaturaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asignatura> getAsignaturaById(@PathVariable Long id) {
        Optional<Asignatura> asignatura = asignaturaService.findById(id);
        return asignatura.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Asignatura createAsignatura(@RequestBody Asignatura asignatura) {
        return asignaturaService.save(asignatura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asignatura> updateAsignatura(@PathVariable Long id, @RequestBody Asignatura asignaturaDetails) {
        Optional<Asignatura> asignatura = asignaturaService.findById(id);
        if (asignatura.isPresent()) {
            asignaturaDetails.setId(id);
            return ResponseEntity.ok(asignaturaService.save(asignaturaDetails));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsignatura(@PathVariable Long id) {
        if (asignaturaService.findById(id).isPresent()) {
            asignaturaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}