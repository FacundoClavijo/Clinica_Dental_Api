package com.dh.ClinicaDentalV2.repository;

import com.dh.ClinicaDentalV2.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    @Query("SELECT t FROM Turno t WHERE t.odontologo.id = ?1")
    Set<Turno> obtenerTurnosOdontologo(Long id);

    @Query("SELECT t FROM Turno t WHERE t.paciente.id = ?1")
    Set<Turno> obtenerTurnosPaciente(Long id);
}
