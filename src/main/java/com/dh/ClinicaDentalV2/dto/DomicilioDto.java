package com.dh.ClinicaDentalV2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DomicilioDto {

    private Long id;
    private String calle;
    private Integer numero;
    private String localidad;
    private String provincia;

    public DomicilioDto() {
    }
}
