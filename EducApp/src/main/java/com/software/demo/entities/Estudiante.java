package com.software.demo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "estudiantes")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo_estudiante", nullable = false, unique = true)
    private String codigoEstudiante;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "semestre_actual", nullable = false)
    private Integer semestreActual;

    @Column(name = "estado", nullable = false)
    private String estado;
    
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<Inscripcion> inscripciones = new ArrayList<>();
    
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<HistorialAcademico> historialAcademico = new ArrayList<>();
    
    // Constructores
    public Estudiante() {}
    
    public Estudiante(String codigoEstudiante, String nombre, String email, Integer semestreActual, String estado) {
        this.codigoEstudiante = codigoEstudiante;
        this.nombre = nombre;
        this.email = email;
        this.semestreActual = semestreActual;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSemestreActual() {
        return semestreActual;
    }

    public void setSemestreActual(Integer semestreActual) {
        this.semestreActual = semestreActual;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public List<HistorialAcademico> getHistorialAcademico() {
        return historialAcademico;
    }

    public void setHistorialAcademico(List<HistorialAcademico> historialAcademico) {
        this.historialAcademico = historialAcademico;
    }

}

