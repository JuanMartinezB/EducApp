package com.software.demo.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InscripcionDTO {
    
    private Long idInscripcion;
    private LocalDate fechaInscripcion;
    private LocalDate fechaCancelacion;
    private String estado;
    private AsignaturaDTO asignatura;
    private String operadorAudit;
    private LocalDateTime auditTimestamp;

    // Constructores
    public InscripcionDTO() {}

    // Getters y Setters
    public Long getIdInscripcion() { return idInscripcion; }
    public void setIdInscripcion(Long idInscripcion) { this.idInscripcion = idInscripcion; }
    public LocalDate getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(LocalDate fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }
    public LocalDate getFechaCancelacion() { return fechaCancelacion; }
    public void setFechaCancelacion(LocalDate fechaCancelacion) { this.fechaCancelacion = fechaCancelacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public AsignaturaDTO getAsignatura() { return asignatura; }
    public void setAsignatura(AsignaturaDTO asignatura) { this.asignatura = asignatura; }
    public String getOperadorAudit() { return operadorAudit; }
    public void setOperadorAudit(String operadorAudit) { this.operadorAudit = operadorAudit; }
    public LocalDateTime getAuditTimestamp() { return auditTimestamp; }
    public void setAuditTimestamp(LocalDateTime auditTimestamp) { this.auditTimestamp = auditTimestamp; }
}
