package com.dh.ClinicaDentalV2.service;

import com.dh.ClinicaDentalV2.dto.OdontologoRequestDto;
import com.dh.ClinicaDentalV2.dto.OdontologoResponseDto;
import com.dh.ClinicaDentalV2.entity.Odontologo;
import com.dh.ClinicaDentalV2.exception.OdontologoBussinessException;
import com.dh.ClinicaDentalV2.exception.OdontologoRepositoryException;
import com.dh.ClinicaDentalV2.repository.OdontologoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
class OdontologoServiceTest {

    @Mock
    private OdontologoRepository odontologoRepository;

    @InjectMocks
    private OdontologoService odontologoService;

    @Mock
    private TurnoService turnoService;

    @Test
    void listarOdontologos() {
        //Arrange
        Odontologo odontologo1 = new Odontologo(1L,12345,"Facu", "Clavijo");
        Odontologo odontologo2 = new Odontologo(2L,23456,"Franco", "Barrera");

        List<Odontologo> odontologoList = new ArrayList<>();
        odontologoList.add(odontologo1);
        odontologoList.add(odontologo2);

        when(odontologoRepository.findAll()).thenReturn(odontologoList);
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());

        OdontologoResponseDto odontologoDto1 = new OdontologoResponseDto(1L,"Facu", "Clavijo", new HashSet<>());
        OdontologoResponseDto odontologoDto2 = new OdontologoResponseDto(2L,"Franco", "Barrera", new HashSet<>());

        Set<OdontologoResponseDto> odontologosEsperados = new HashSet<>();
        odontologosEsperados.add(odontologoDto1);
        odontologosEsperados.add(odontologoDto2);

        //Act
        Set<OdontologoResponseDto> odontologosDevueltos = odontologoService.listarOdontologos();
        //Assert
        Assertions.assertIterableEquals(odontologosEsperados, odontologosDevueltos);

    }

    @Test
    void agregarOdontologo() {
        //Arrange
        Odontologo odontologo = new Odontologo(1L,12345,"Facu", "Clavijo");
        doReturn(odontologo).when(odontologoRepository).save(any());
        when(odontologoRepository.findByNroMatricula(any())).thenReturn(null);
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());
        OdontologoRequestDto odontologoRequest = new OdontologoRequestDto(12345,"Facu", "Clavijo");
        //Act
        //Assert
        Assertions.assertDoesNotThrow(()->{
            odontologoService.agregarOdontologo(odontologoRequest);
        });
    }
    @Test
    void agregarOdontologo_con_parametro_incorrecto(){
        //Arrange
        Odontologo odontologo = new Odontologo(1L,12345,"Facu", "Clavijo");
        doReturn(odontologo).when(odontologoRepository).save(any());
        when(odontologoRepository.findByNroMatricula(any())).thenReturn(null);
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());
        OdontologoRequestDto odontologoRequest = null;
        //Act
        //Assert
        Assertions.assertThrows(OdontologoBussinessException.class, ()->{
            odontologoService.agregarOdontologo(odontologoRequest);
        });
    }
    @Test
    void agregarOdontologo_con_nombre_incorrecto(){
        //Arrange
        Odontologo odontologo = new Odontologo(1L,12345,"Facu", "Clavijo");
        doReturn(odontologo).when(odontologoRepository).save(any());
        when(odontologoRepository.findByNroMatricula(any())).thenReturn(null);
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());
        OdontologoRequestDto odontologoRequest = new OdontologoRequestDto(12345,"", "Clavijo");
        //Act
        //Assert
        Assertions.assertThrows(OdontologoBussinessException.class, ()->{
            odontologoService.agregarOdontologo(odontologoRequest);
        });
    }
    @Test
    void agregarOdontologo_con_apellido_incorrecto(){
        //Arrange
        Odontologo odontologo = new Odontologo(1L,12345,"Facu", "Clavijo");
        doReturn(odontologo).when(odontologoRepository).save(any());

        when(odontologoRepository.findByNroMatricula(any())).thenReturn(null);
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());

        OdontologoRequestDto odontologoRequest = new OdontologoRequestDto(12345,"Facu", null);
        //Act
        //Assert
        Assertions.assertThrows(OdontologoBussinessException.class, ()->{
            odontologoService.agregarOdontologo(odontologoRequest);
        });
    }
    @Test
    void agregarOdontologo_con_matricula_incorrecta(){
        //Arrange
        Odontologo odontologo = new Odontologo(1L,12345,"Facu", "Clavijo");
        doReturn(odontologo).when(odontologoRepository).save(any());

        when(odontologoRepository.findByNroMatricula(any())).thenReturn(null);
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());

        OdontologoRequestDto odontologoRequest = new OdontologoRequestDto(null,"Facu", "Clavijo");
        //Act
        //Assert
        Assertions.assertThrows(OdontologoBussinessException.class, ()->{
            odontologoService.agregarOdontologo(odontologoRequest);
        });
    }
    @Test
    void agregarOdontologo_con_matricula_ya_en_uso(){
        //Arrange
        Odontologo odontologo = new Odontologo(1L,12345,"Facu", "Clavijo");
        doReturn(odontologo).when(odontologoRepository).save(any());

        Odontologo odontologoXMatricula = new Odontologo(1L,12345,"Facu", "Clavijo");
        when(odontologoRepository.findByNroMatricula(any())).thenReturn(odontologoXMatricula);
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());

        OdontologoRequestDto odontologoRequest = new OdontologoRequestDto(123456,"Facu", "Clavijo");
        //Act
        //Assert
        Assertions.assertThrows(OdontologoRepositoryException.class, ()->{
            odontologoService.agregarOdontologo(odontologoRequest);
        });
    }

    @Test
    void modificarOdontologo() {
        //Arrange
        Optional<Odontologo> odontologo = Optional.of(new Odontologo(1L,12345,"Facu", "Clavijo"));
        doReturn(odontologo).when(odontologoRepository).findById(any());
        when(odontologoRepository.findByNroMatricula(any())).thenReturn(null);
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());
        OdontologoRequestDto odontologoRequest = new OdontologoRequestDto(12345,"Facundo", "Gutierrez");
        //Act
        //Assert
        Assertions.assertDoesNotThrow(()->{
            odontologoService.modificarOdontologo(1L,odontologoRequest);
        });
    }
    @Test
    void modificarOdontologo_con_odontologo_a_modificar_incorrecto() {
        //Arrange
        Optional<Odontologo> odontologo = Optional.of(new Odontologo(1L,12345,"Facu", "Clavijo"));
        doReturn(odontologo).when(odontologoRepository).findById(any());
        when(odontologoRepository.findByNroMatricula(any())).thenReturn(null);
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());
        OdontologoRequestDto odontologoRequest = null;
        //Act
        //Assert
        Assertions.assertThrows(OdontologoBussinessException.class,()->{
            odontologoService.modificarOdontologo(1L,odontologoRequest);
        });
    }
    @Test
    void modificarOdontologo_con_nombre_a_modificar_incorrecto() {
        //Arrange
        Optional<Odontologo> odontologo = Optional.of(new Odontologo(1L,12345,"Facu", "Clavijo"));
        doReturn(odontologo).when(odontologoRepository).findById(any());
        when(odontologoRepository.findByNroMatricula(any())).thenReturn(null);
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());
        OdontologoRequestDto odontologoRequest = new OdontologoRequestDto(12345,"", "Gutierrez");
        //Act
        //Assert
        Assertions.assertThrows(OdontologoBussinessException.class,()->{
            odontologoService.modificarOdontologo(1L,odontologoRequest);
        });
    }
    @Test
    void modificarOdontologo_con_apellido_a_modificar_incorrecto() {
        //Arrange
        Optional<Odontologo> odontologo = Optional.of(new Odontologo(1L,12345,"Facu", "Clavijo"));
        doReturn(odontologo).when(odontologoRepository).findById(any());
        when(odontologoRepository.findByNroMatricula(any())).thenReturn(null);
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());
        OdontologoRequestDto odontologoRequest = new OdontologoRequestDto(12345,"Facu", "");
        //Act
        //Assert
        Assertions.assertThrows(OdontologoBussinessException.class,()->{
            odontologoService.modificarOdontologo(1L,odontologoRequest);
        });
    }
    @Test
    void modificarOdontologo_con_matricula_a_modificar_incorrecta() {
        //Arrange
        Optional<Odontologo> odontologo = Optional.of(new Odontologo(1L,12345,"Facu", "Clavijo"));
        doReturn(odontologo).when(odontologoRepository).findById(any());
        when(odontologoRepository.findByNroMatricula(any())).thenReturn(null);
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());
        OdontologoRequestDto odontologoRequest = new OdontologoRequestDto(null,"Facu", "Gutierrez");
        //Act
        //Assert
        Assertions.assertThrows(OdontologoBussinessException.class,()->{
            odontologoService.modificarOdontologo(1L,odontologoRequest);
        });
    }
    @Test
    void modificarOdontologo_con_odontologo_no_encontrado() {
        //Arrange
        doReturn(Optional.empty()).when(odontologoRepository).findById(any());
        when(odontologoRepository.findByNroMatricula(any())).thenReturn(null);
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());
        OdontologoRequestDto odontologoRequest = new OdontologoRequestDto(1243568,"Facu", "Gutierrez");
        //Act
        //Assert
        Assertions.assertThrows(OdontologoBussinessException.class,()->{
            odontologoService.modificarOdontologo(1L,odontologoRequest);
        });
    }
    @Test
    void modificarOdontologo_con_matricula_ya_existente() {
        //Arrange
        Optional<Odontologo> odontologo = Optional.of(new Odontologo(1L,12345,"Facu", "Clavijo"));
        doReturn(odontologo).when(odontologoRepository).findById(any());
        Odontologo odontologoXMatricula = new Odontologo(1L,12345,"Facu", "Clavijo");
        when(odontologoRepository.findByNroMatricula(any())).thenReturn(odontologoXMatricula);
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());
        OdontologoRequestDto odontologoRequest = new OdontologoRequestDto(1243568,"Facu", "Gutierrez");
        //Act
        //Assert
        Assertions.assertThrows(OdontologoRepositoryException.class,()->{
            odontologoService.modificarOdontologo(1L,odontologoRequest);
        });
    }

    @Test
    void buscarOdontologo() {
        //Arrange
        Optional<Odontologo> odontologo = Optional.of(new Odontologo(1L,12345,"Facu", "Clavijo"));
        doReturn(odontologo).when(odontologoRepository).findById(any());
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());
        //Act
        //Assert
        Assertions.assertDoesNotThrow(()->{
            odontologoService.buscarOdontologo(1L);
        });
    }
    @Test
    void buscarOdontologo_con_id_incorrecto() {
        //Arrange
        Optional<Odontologo> odontologo = Optional.of(new Odontologo(1L,12345,"Facu", "Clavijo"));
        doReturn(odontologo).when(odontologoRepository).findById(any());
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());
        //Act
        //Assert
        Assertions.assertThrows(OdontologoBussinessException.class,()->{
            odontologoService.buscarOdontologo(-4L);
        });
    }
    @Test
    void buscarOdontologo_con_odontologo_no_encontrado() {
        //Arrange
        doReturn(Optional.empty()).when(odontologoRepository).findById(any());
        when(turnoService.obtenerTurnoOdontologo(any())).thenReturn(new HashSet<>());
        //Act
        //Assert
        Assertions.assertThrows(OdontologoBussinessException.class,()->{
            odontologoService.buscarOdontologo(4L);
        });
    }

    @Test
    void eliminarOdontologo() {
        //Arrange
        Optional<Odontologo> odontologo = Optional.of(new Odontologo(1L,12345,"Facu", "Clavijo"));
        doReturn(odontologo).when(odontologoRepository).findById(any());
        //Act
        //Assert
        Assertions.assertDoesNotThrow(()->{
            odontologoService.eliminarOdontologo(1L);
        });
    }
    @Test
    void eliminarOdontologo_con_id_incorrecto() {
        //Arrange
        Optional<Odontologo> odontologo = Optional.of(new Odontologo(1L,12345,"Facu", "Clavijo"));
        doReturn(odontologo).when(odontologoRepository).findById(any());
        //Act
        //Assert
        Assertions.assertThrows(OdontologoBussinessException.class,()->{
            odontologoService.eliminarOdontologo(-4L);
        });
    }
    @Test
    void eliminarOdontologo_con_odontologo_no_encontrado() {
        //Arrange
        doReturn(Optional.empty()).when(odontologoRepository).findById(any());
        //Act
        //Assert
        Assertions.assertThrows(OdontologoBussinessException.class,()->{
            odontologoService.eliminarOdontologo(4L);
        });
    }
}