package com.software.demo.repositories;

import com.software.demo.entities.HistorialAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HistorialAcademicoRepository extends JpaRepository<HistorialAcademico, Long> {

}