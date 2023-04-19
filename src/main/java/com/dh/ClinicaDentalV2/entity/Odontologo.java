package com.dh.ClinicaDentalV2.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
@Entity
@Table(name = "Odontologos")
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 8, nullable = false, unique = true)
    private Integer nroMatricula;
    @Column(length = 25, nullable = false)
    private String nombre;
    @Column(length = 25, nullable = false)
    private String Apellido;



    public Odontologo(){

    }

    public Odontologo(Long id, Integer nroMatricula, String nombre, String apellido) {
        this.id = id;
        this.nroMatricula = nroMatricula;
        this.nombre = nombre;
        Apellido = apellido;
    }
}
