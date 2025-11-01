package com.software.demo.repositories;

import com.software.demo.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

}