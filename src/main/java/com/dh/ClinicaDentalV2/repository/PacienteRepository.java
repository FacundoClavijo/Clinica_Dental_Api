package com.dh.ClinicaDentalV2.repository;



import com.dh.ClinicaDentalV2.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository  extends JpaRepository<Paciente, Long> {

    @Query("SELECT p FROM Paciente p WHERE p.dni = ?1")
    Paciente findByDni(String dni);

}
