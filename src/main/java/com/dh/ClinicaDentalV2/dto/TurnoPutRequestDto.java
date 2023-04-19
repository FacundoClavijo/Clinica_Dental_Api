package com.dh.ClinicaDentalV2.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoPutRequestDto {
    private PacienteDto paciente;
    private OdontologoDto odontologo;
    private String fecha;
    private String hora;

    public TurnoPutRequestDto() {
    }
}
