package com.dh.ClinicaDentalV2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PacienteResponseDto {

    private Long id;
    private String nombre;
    private String apellido;
    private Integer dni;
    private Set<TurnoPacienteDto> turnos;

}
