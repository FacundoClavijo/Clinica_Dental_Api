package com.dh.ClinicaDentalV2.repository;

import com.dh.ClinicaDentalV2.entity.Odontologo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {

    @Query("SELECT o FROM Odontologo o WHERE o.nroMatricula = ?1")
    Odontologo findByNroMatricula(Integer nroMatricula);

}
