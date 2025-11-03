package com.software.demo.mappers;

import com.software.demo.dtos.InscripcionDTO;
import com.software.demo.entities.Inscripcion;

public class InscripcionMapper {

    public static InscripcionDTO toInscripcionDTO(Inscripcion inscripcion) {
        if (inscripcion == null) return null;

        InscripcionDTO dto = new InscripcionDTO();
        dto.setIdInscripcion(inscripcion.getId());
        dto.setFechaInscripcion(inscripcion.getFechaInscripcion());
        dto.setFechaCancelacion(inscripcion.getFechaCancelacion());
        dto.setEstado(inscripcion.getEstado());
        
        if (inscripcion.getAsignatura() != null) {
            dto.setAsignatura(AsignaturaMapper.toAsignaturaDTO(inscripcion.getAsignatura()));
        }

        dto.setOperadorAudit(inscripcion.getOperadorAudit());
        dto.setAuditTimestamp(inscripcion.getAuditTimestamp());

        return dto;
    }
}
