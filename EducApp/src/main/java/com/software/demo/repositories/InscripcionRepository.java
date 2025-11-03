package com.software.demo.repositories;

import com.software.demo.entities.Inscripcion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    List<Inscripcion> findByEstudianteIdAndEstado(Long estudianteId, String estado);

    List<Inscripcion> findByEstudianteId(Long estudianteId);
    
    boolean existsByEstudianteIdAndAsignaturaIdAndEstado(Long estudianteId, Long asignaturaId, String estado);
}