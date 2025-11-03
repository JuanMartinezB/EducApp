package com.software.demo.dtos;

public class HistorialAcademicoRequestDTO {
    
    private Long estudianteId;
    private Long asignaturaId;
    private Double nota;
    private Integer semestre;

    public HistorialAcademicoRequestDTO() {}

    // Getters y Setters...
    public Long getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Long estudianteId) { this.estudianteId = estudianteId; }
    public Long getAsignaturaId() { return asignaturaId; }
    public void setAsignaturaId(Long asignaturaId) { this.asignaturaId = asignaturaId; }
    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }
    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }
}
