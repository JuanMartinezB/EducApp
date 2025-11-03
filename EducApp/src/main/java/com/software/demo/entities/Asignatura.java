package com.software.demo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "asignaturas")
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "creditos", nullable = false)
    private Integer creditos;

    @Column(name = "cupo_maximo", nullable = false)
    private Integer cupoMaximo;

    @Column(name = "cupo_actual", nullable = false)
    private Integer cupoActual = 0;

    @Column(name = "semestre", nullable = false)
    private Integer semestre;

    @Column(name = "horario")
    private String horario;
    
    @ManyToMany
    @JoinTable(
        name = "prerrequisitos",
        joinColumns = @JoinColumn(name = "asignatura_id"),
        inverseJoinColumns = @JoinColumn(name = "prerrequisito_id")
    )
    private List<Asignatura> prerrequisitos = new ArrayList<>();
    
    @OneToMany(mappedBy = "asignatura")
    private List<Inscripcion> inscripciones = new ArrayList<>();
    
    // Constructores
    public Asignatura() {}
    
    public Asignatura(String codigo, String nombre, Integer creditos, Integer cupoMaximo, Integer semestre, String horario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.cupoMaximo = cupoMaximo;
        this.semestre = semestre;
        this.horario = horario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public Integer getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(Integer cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public Integer getCupoActual() {
        return cupoActual;
    }

    public void setCupoActual(Integer cupoActual) {
        this.cupoActual = cupoActual;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public List<Asignatura> getPrerrequisitos() {
        return prerrequisitos;
    }

    public void setPrerrequisitos(List<Asignatura> prerrequisitos) {
        this.prerrequisitos = prerrequisitos;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
    

}