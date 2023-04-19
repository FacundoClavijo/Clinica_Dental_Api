package com.dh.ClinicaDentalV2.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table (name = "Pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 25, nullable = false)
    private String nombre;
    @Column(length = 25, nullable = false)
    private String apellido;
    @Column(length = 8, nullable = false, unique = true)
    private String dni;
    @Column(length = 25, nullable = false)
    private String fechaAlta;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "domicilioId",nullable = false)
    private Domicilio domicilio;

    public Paciente() {

    }
}
