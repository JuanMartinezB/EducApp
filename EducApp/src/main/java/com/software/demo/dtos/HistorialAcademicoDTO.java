package com.software.demo.dtos;

public class HistorialAcademicoDTO {
    
    private Long id;
    private Double nota;
    private Integer semestre;
    private Long estudianteId; 
    private String estudianteNombre;
    private AsignaturaDTO asignatura;

    public HistorialAcademicoDTO() {}

    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getNota() { return nota; }
    public void setNota(Double nota) { this.nota = nota; }
    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }
    public Long getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Long estudianteId) { this.estudianteId = estudianteId; }
    public String getEstudianteNombre() { return estudianteNombre; }
    public void setEstudianteNombre(String estudianteNombre) { this.estudianteNombre = estudianteNombre; }
    public AsignaturaDTO getAsignatura() { return asignatura; }
    public void setAsignatura(AsignaturaDTO asignatura) { this.asignatura = asignatura; }
}
