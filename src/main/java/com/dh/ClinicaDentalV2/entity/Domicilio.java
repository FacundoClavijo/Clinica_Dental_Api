package com.dh.ClinicaDentalV2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Domicilios")
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 25)
    private String calle;
    @Column(length = 25)
    private Integer numero;
    @Column(length = 25, nullable = false)
    private String localidad;
    @Column(length = 25, nullable = false)
    private String provincia;

    public Domicilio() {

    }

}
