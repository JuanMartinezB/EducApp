package com.software.demo.repositories;

import com.software.demo.entities.HistorialAcademico;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HistorialAcademicoRepository extends JpaRepository<HistorialAcademico, Long> {

    List<HistorialAcademico> findByEstudianteId(Long estudianteId);

    Optional<HistorialAcademico> findByEstudianteIdAndAsignaturaId(Long estudianteId, Long asignaturaId);

    boolean existsByEstudianteIdAndAsignaturaIdAndNotaGreaterThanEqual(Long estudianteId, Long asignaturaId, Double nota);
}