package com.software.demo.dtos;

public class InscripcionRequestDTO {
    
    private Long estudianteId;
    private Long asignaturaId;
    private String operador;

    public InscripcionRequestDTO() {}

    // Getters
    public Long getEstudianteId() {
        return estudianteId;
    }

    public Long getAsignaturaId() {
        return asignaturaId;
    }

    public String getOperador() {
        return operador;
    }

    // Setters
    public void setEstudianteId(Long estudianteId) {
        this.estudianteId = estudianteId;
    }

    public void setAsignaturaId(Long asignaturaId) {
        this.asignaturaId = asignaturaId;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }
}
