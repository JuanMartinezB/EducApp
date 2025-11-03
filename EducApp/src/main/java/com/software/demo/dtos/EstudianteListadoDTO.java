package com.software.demo.dtos;

public class EstudianteListadoDTO {
    
    private Long id;
    private String codigoEstudiante;
    private String nombre;
    private String email;
    private Integer semestreActual;
    private String estado; 

    public EstudianteListadoDTO() {}

    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoEstudiante() { return codigoEstudiante; }
    public void setCodigoEstudiante(String codigoEstudiante) { this.codigoEstudiante = codigoEstudiante; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getSemestreActual() { return semestreActual; }
    public void setSemestreActual(Integer semestreActual) { this.semestreActual = semestreActual; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
