package com.dh.ClinicaDentalV2.dto;

import com.dh.ClinicaDentalV2.entity.Odontologo;
import com.dh.ClinicaDentalV2.entity.Paciente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoRequestDto {

    private Long paciente;

    private Long odontologo;

    private String fecha;

    private String hora;
}
