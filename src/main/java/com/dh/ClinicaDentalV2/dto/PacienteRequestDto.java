package com.dh.ClinicaDentalV2.dto;

import com.dh.ClinicaDentalV2.entity.Domicilio;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteRequestDto {

    private String nombre;
    private String apellido;
    private String dni;
    private String fechaAlta;
    private DomicilioRequestDto domicilio;
}
