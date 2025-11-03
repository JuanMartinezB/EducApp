package com.software.demo.mappers;

import com.software.demo.dtos.HistorialAcademicoDTO;
import com.software.demo.entities.HistorialAcademico;

public class HistorialAcademicoMapper {

    public static HistorialAcademicoDTO toHistorialAcademicoDTO(HistorialAcademico historial) {
        if (historial == null) return null;

        HistorialAcademicoDTO dto = new HistorialAcademicoDTO();
        
        dto.setId(historial.getId());
        dto.setNota(historial.getNota());
        dto.setSemestre(historial.getSemestre());
        
        if (historial.getEstudiante() != null) {
            dto.setEstudianteId(historial.getEstudiante().getId());
            dto.setEstudianteNombre(historial.getEstudiante().getNombre());
        }

        if (historial.getAsignatura() != null) {
            dto.setAsignatura(AsignaturaMapper.toAsignaturaDTO(historial.getAsignatura()));
        }

        return dto;
    }
}
