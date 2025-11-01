package com.software.demo.repositories;

import com.software.demo.entities.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {

}