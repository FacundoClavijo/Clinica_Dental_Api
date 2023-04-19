package com.dh.ClinicaDentalV2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OdontologoResponseDto {

    private Long id;
    private String nombre;
    private String apellido;

    private Integer nroMatricula;
    private Set<TurnoOdontologoDto> turnos;

    public OdontologoResponseDto(Long id, String nombre, String apellido, Set<TurnoOdontologoDto> turnos) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.turnos = turnos;
    }

    public OdontologoResponseDto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OdontologoResponseDto that = (OdontologoResponseDto) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(apellido, that.apellido) && Objects.equals(turnos, that.turnos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, turnos);
    }
}
