package com.software.demo.dtos;

import java.util.List;

public class AsignaturaRequestDTO {
    
    private String codigo;
    private String nombre;
    private Integer creditos;
    private Integer cupoMaximo;
    private Integer semestre;
    private String horario;
    
    // 🚨 Nueva lista para recibir solo IDs de los prerrequisitos 🚨
    private List<Long> prerrequisitosIds;

    public AsignaturaRequestDTO() {}

    // Getters y Setters...
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }
    public Integer getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(Integer cupoMaximo) { this.cupoMaximo = cupoMaximo; }
    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    public List<Long> getPrerrequisitosIds() { return prerrequisitosIds; }
    public void setPrerrequisitosIds(List<Long> prerrequisitosIds) { this.prerrequisitosIds = prerrequisitosIds; }
}