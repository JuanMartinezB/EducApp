package com.software.demo.repositories;

import com.software.demo.entities.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {

    @Modifying
    @Transactional
    @Query("update Asignatura a set a.cupoActual = a.cupoActual + 1 where a.id = :id and a.cupoActual < a.cupoMaximo")
    int incrementCupoIfAvailable(Long id);

    @Modifying
    @Transactional
    @Query("update Asignatura a set a.cupoActual = a.cupoActual - 1 where a.id = :id and a.cupoActual > 0")
    int decrementCupoIfPossible(Long id);
}