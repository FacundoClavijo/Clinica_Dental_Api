package com.dh.ClinicaDentalV2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoResponseDto {
    private Long paciente;

    private Long odontologo;

    private String fecha;

    private String hora;
}
