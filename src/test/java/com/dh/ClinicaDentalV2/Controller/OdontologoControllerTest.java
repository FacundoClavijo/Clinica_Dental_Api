package com.dh.ClinicaDentalV2.Controller;

import com.dh.ClinicaDentalV2.dto.OdontologoRequestDto;
import com.dh.ClinicaDentalV2.dto.OdontologoResponseDto;
import com.dh.ClinicaDentalV2.exception.OdontologoBussinessException;
import com.dh.ClinicaDentalV2.exception.OdontologoRepositoryException;
import com.dh.ClinicaDentalV2.service.OdontologoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OdontologoControllerTest {

    @Mock
    private OdontologoService service;

    @InjectMocks
    private OdontologoController controller;

    @Test
    void listarOdontologos() {
        Set<OdontologoResponseDto> odontologos = new HashSet<OdontologoResponseDto>();
        odontologos.add(new OdontologoResponseDto(1L, "nombre", "apellido", new HashSet<>()));
        when(service.listarOdontologos()).thenReturn(odontologos);

        //Act
        ResponseEntity<Set<OdontologoResponseDto>> response = controller.listarOdontologos();

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(odontologos, response.getBody());
    }

    @Test
    void listarOdontologos_devuelve_respuesta_500() {
        //Arrange
        when(service.listarOdontologos()).thenThrow(DataIntegrityViolationException.class);
        //Act
        ResponseEntity<Set<OdontologoResponseDto>> response = controller.listarOdontologos();
        //Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void buscarOdontologo() throws OdontologoBussinessException {
        //Arrange
        OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto(1L, "Facundo", "Clavijo", new HashSet<>());
        when(service.buscarOdontologo(any())).thenReturn(odontologoResponseDto);
        //Act
        ResponseEntity<OdontologoResponseDto> response = controller.buscarOdontologo(1L);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void buscarOdontologo_devuelve_respuesta_400() throws OdontologoBussinessException {
        //Arrange
        when(service.buscarOdontologo(any())).thenThrow(OdontologoBussinessException.class);
        //Act
        ResponseEntity<OdontologoResponseDto> response = controller.buscarOdontologo(1L);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void buscarOdontologo_devuelve_respuesta_500() throws OdontologoBussinessException {
        //Arrange
        when(service.buscarOdontologo(any())).thenThrow(DataIntegrityViolationException.class);
        //Act
        ResponseEntity<OdontologoResponseDto> response = controller.buscarOdontologo(1L);
        //Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void agregarOdontologo() throws OdontologoBussinessException, OdontologoRepositoryException {
        //Arrange
        OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto(1L, "Facundo", "Clavijo", new HashSet<>());
        when(service.agregarOdontologo(any())).thenReturn(odontologoResponseDto);
        OdontologoRequestDto requestDto = new OdontologoRequestDto(12345, "Facundo", "Clavijo");
        //Act
        ResponseEntity<OdontologoResponseDto> response = controller.agregarOdontologo(requestDto);
        //Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void agregarOdontologo_devuelve_respuesta_400() throws OdontologoBussinessException, OdontologoRepositoryException {
        //Arrange
        when(service.agregarOdontologo(any())).thenThrow(OdontologoBussinessException.class);
        OdontologoRequestDto requestDto = new OdontologoRequestDto(12345, "", "Clavijo");
        //Act
        ResponseEntity<OdontologoResponseDto> response = controller.agregarOdontologo(requestDto);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void agregarOdontologo_devuelve_respuesta_500() throws OdontologoBussinessException, OdontologoRepositoryException {
        //Arrange
        when(service.agregarOdontologo(any())).thenThrow(OdontologoRepositoryException.class);
        OdontologoRequestDto requestDto = new OdontologoRequestDto(12345, "Facu", "Clavijo");
        //Act
        ResponseEntity<OdontologoResponseDto> response = controller.agregarOdontologo(requestDto);
        //Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void modificarOdontologo() throws OdontologoBussinessException, OdontologoRepositoryException {
        //Arrange
        OdontologoResponseDto responseDto = new OdontologoResponseDto(1L, "Facu", "Gutierrez", new HashSet<>());
        when(service.modificarOdontologo(any(), any())).thenReturn(responseDto);
        OdontologoRequestDto requestDto = new OdontologoRequestDto(1234, "Facu", "Gutierrez");
        //Act
        ResponseEntity<OdontologoResponseDto> response = controller.modificarOdontologo(1L, requestDto);
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void modificarOdontologo_devuelve_respuesta_400() throws OdontologoBussinessException, OdontologoRepositoryException {
        //Arrange
        when(service.modificarOdontologo(any(), any())).thenThrow(OdontologoBussinessException.class);
        OdontologoRequestDto requestDto = new OdontologoRequestDto(1234, "", "Gutierrez");
        //Act
        ResponseEntity<OdontologoResponseDto> response = controller.modificarOdontologo(1L, requestDto);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void modificarOdontologo_devuelve_respuesta_500() throws OdontologoBussinessException, OdontologoRepositoryException {
        //Arrange
        when(service.modificarOdontologo(any(), any())).thenThrow(OdontologoRepositoryException.class);
        OdontologoRequestDto requestDto = new OdontologoRequestDto(1234, "", "Gutierrez");
        //Act
        ResponseEntity<OdontologoResponseDto> response = controller.modificarOdontologo(1L, requestDto);
        //Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void eliminarOdontologo() throws OdontologoBussinessException {
        //Arrange
        doNothing().when(service).eliminarOdontologo(any());
        //Act
        ResponseEntity<OdontologoResponseDto> response = controller.eliminarOdontologo(1L);
        //Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void eliminarOdontologo_devuelve_respuesta_400() throws OdontologoBussinessException {
        //Arrange
        doThrow(OdontologoBussinessException.class).when(service).eliminarOdontologo(any());
        //Act
        ResponseEntity<OdontologoResponseDto> response = controller.eliminarOdontologo(1L);
        //Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void eliminarOdontologo_devuelve_respuesta_500() throws OdontologoBussinessException {
        //Arrange
        doThrow(DataIntegrityViolationException.class).when(service).eliminarOdontologo(any());
        //Act
        ResponseEntity<OdontologoResponseDto> response = controller.eliminarOdontologo(1L);
        //Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}