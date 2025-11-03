package com.software.demo.dtos;

import java.util.List;

public class EstudianteLoginDTO {
    
    private Long id;
    private String codigo;
    private String nombreCompleto;
    private String email;
    private String estado;
    private List<InscripcionDTO> inscripciones;

    public EstudianteLoginDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<InscripcionDTO> getInscripciones() { return inscripciones; }
    public void setInscripciones(List<InscripcionDTO> inscripciones) { this.inscripciones = inscripciones; }
}
