package com.software.demo.mappers;

import com.software.demo.dtos.AsignaturaDTO;
import com.software.demo.entities.Asignatura;

public class AsignaturaMapper {

    public static AsignaturaDTO toAsignaturaDTO(Asignatura asignatura) {
        if (asignatura == null) return null;

        AsignaturaDTO dto = new AsignaturaDTO();
        dto.setId(asignatura.getId());
        dto.setCodigo(asignatura.getCodigo());
        dto.setNombre(asignatura.getNombre());
        dto.setCreditos(asignatura.getCreditos());
        dto.setCupoMaximo(asignatura.getCupoMaximo());
        dto.setCupoActual(asignatura.getCupoActual());
        dto.setSemestre(asignatura.getSemestre());
        dto.setHorario(asignatura.getHorario());
        
        return dto;
    }
    
}
