package com.software.demo.mappers;

import java.util.stream.Collectors;

import com.software.demo.dtos.EstudianteListadoDTO;
import com.software.demo.dtos.EstudianteLoginDTO;
import com.software.demo.entities.Estudiante;

public class EstudianteMapper {

    // 1. Mapeo a DTO para LISTAS (Sin colecciones para evitar bucles)
    public static EstudianteListadoDTO toEstudianteListadoDTO(Estudiante estudiante) {
        if (estudiante == null) return null;

        EstudianteListadoDTO dto = new EstudianteListadoDTO();
        dto.setId(estudiante.getId());
        dto.setCodigoEstudiante(estudiante.getCodigoEstudiante());
        dto.setNombre(estudiante.getNombre());
        dto.setEmail(estudiante.getEmail());
        dto.setSemestreActual(estudiante.getSemestreActual());
        dto.setEstado(estudiante.getEstado());
        
        // NO se mapean Inscripciones ni Historial Académico
        return dto;
    }

    // 2. Mapeo a DTO para LOGIN/PERFIL (Incluye inscripciones como DTOs)
    public static EstudianteLoginDTO toEstudianteLoginDTO(Estudiante estudiante) {
        if (estudiante == null) return null;

        EstudianteLoginDTO dto = new EstudianteLoginDTO();
        dto.setId(estudiante.getId());
        dto.setCodigo(estudiante.getCodigoEstudiante());
        dto.setNombreCompleto(estudiante.getNombre());
        dto.setEmail(estudiante.getEmail());
        dto.setEstado(estudiante.getEstado());
        
        // Mapea la colección de Inscripciones a DTOs de Inscripción (limpios)
        if (estudiante.getInscripciones() != null) {
            dto.setInscripciones(estudiante.getInscripciones().stream()
                .map(InscripcionMapper::toInscripcionDTO)
                .collect(Collectors.toList()));
        }

        return dto;
    }
}