package com.software.demo.controllers;

import com.software.demo.dtos.AsignaturaDTO;
import com.software.demo.dtos.AsignaturaRequestDTO;
import com.software.demo.entities.Asignatura;
import com.software.demo.mappers.AsignaturaMapper;
import com.software.demo.services.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/asignaturas")
@CrossOrigin(origins = "http://localhost:4200")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    @GetMapping
    public List<AsignaturaDTO> getAllAsignaturas() {
        return asignaturaService.findAll().stream()
                .map(AsignaturaMapper::toAsignaturaDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsignaturaDTO> getAsignaturaById(@PathVariable Long id) {
        return asignaturaService.findById(id)
                .map(AsignaturaMapper::toAsignaturaDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AsignaturaDTO createAsignatura(@RequestBody AsignaturaRequestDTO asignaturaRequest) {
        Asignatura nuevaAsignatura = asignaturaService.saveFromDTO(asignaturaRequest);
        return AsignaturaMapper.toAsignaturaDTO(nuevaAsignatura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AsignaturaDTO> updateAsignatura(@PathVariable Long id, @RequestBody AsignaturaRequestDTO asignaturaDetails) {
        Optional<Asignatura> asignaturaOptional = asignaturaService.findById(id);
        
        if (asignaturaOptional.isPresent()) {
            Asignatura updatedAsignatura = asignaturaService.updateFromDTO(id, asignaturaDetails);
            return ResponseEntity.ok(AsignaturaMapper.toAsignaturaDTO(updatedAsignatura));
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