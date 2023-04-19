package com.dh.ClinicaDentalV2.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OdontologoRequestDto {

    private Integer nroMatricula;
    private String nombre;
    private String Apellido;

    public OdontologoRequestDto(Integer nroMatricula, String nombre, String apellido) {
        this.nroMatricula = nroMatricula;
        this.nombre = nombre;
        Apellido = apellido;
    }

    public OdontologoRequestDto() {
    }
}
