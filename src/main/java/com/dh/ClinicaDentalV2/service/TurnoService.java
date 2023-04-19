package com.dh.ClinicaDentalV2.service;


import com.dh.ClinicaDentalV2.dto.*;
import com.dh.ClinicaDentalV2.entity.Odontologo;
import com.dh.ClinicaDentalV2.entity.Paciente;
import com.dh.ClinicaDentalV2.exception.OdontologoBussinessException;
import com.dh.ClinicaDentalV2.exception.PacienteBussinessException;
import com.dh.ClinicaDentalV2.exception.TurnoBussinessException;
import com.dh.ClinicaDentalV2.entity.Turno;
import com.dh.ClinicaDentalV2.repository.TurnoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);

    public Set<TurnoDto> listarTurnos(){
        List<Turno> turnos = turnoRepository.findAll();
        Set<TurnoDto> turnoDtos = new HashSet<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for( Turno turno : turnos){

            turnoDtos.add(objectMapper.convertValue(turno, TurnoDto.class));
        }
        LOGGER.info("Se listaron correctamente los turnos en la capa de servicio");
        return turnoDtos;
    }

    public TurnoResponseDto agregarTurno(TurnoRequestDto turnoRequest) throws TurnoBussinessException, PacienteBussinessException, OdontologoBussinessException {
        if (turnoRequest == null) {
            LOGGER.error("El turno no puede ser nulo");
            throw new TurnoBussinessException("El turno no puede ser nulo");
        }
        if (turnoRequest.getFecha() == null || turnoRequest.getFecha().isEmpty()) {
            LOGGER.error("La fecha del turno no puede ser nula");
            throw new TurnoBussinessException("La fecha del turno no puede ser nula o vacía");
        }
        if (turnoRequest.getHora() == null|| turnoRequest.getHora().isEmpty()) {
            LOGGER.error("La hora del turno no puede ser nula");
            throw new TurnoBussinessException("La hora del turno no puede ser nula o vacía");
        }
        if (turnoRequest.getPaciente() == null) {
            LOGGER.error("El ID del paciente del turno no puede ser nulo");
            throw new TurnoBussinessException("El paciente del turno no puede ser nulo");
        }
        if (turnoRequest.getOdontologo() == null) {
            LOGGER.error("El ID del odontólogo del turno no puede ser nulo");
            throw new TurnoBussinessException("El odontólogo del turno no puede ser nulo");
        }

        Paciente paciente = new Paciente();
        paciente.setId(turnoRequest.getPaciente());
        Odontologo odontologo = new Odontologo();
        odontologo.setId(turnoRequest.getOdontologo());

        Turno turno = new Turno();
        turno.setFecha(turnoRequest.getFecha());
        turno.setHora(turnoRequest.getHora());
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);

        Turno turnoGuardado = turnoRepository.save(turno);

        TurnoResponseDto turnoResponseDto = new TurnoResponseDto();

        turnoResponseDto.setOdontologo(turnoGuardado.getOdontologo().getId());
        turnoResponseDto.setPaciente(turnoGuardado.getPaciente().getId());
        turnoResponseDto.setFecha(turnoGuardado.getFecha());
        turnoResponseDto.setHora(turnoGuardado.getHora());

        LOGGER.info("Se agregó correctamente el turno en la capa de servicio");
        return turnoResponseDto;
    }

    @Transactional
    public TurnoResponseDto modificarTurno(Long id, TurnoRequestDto updatedTurno) throws TurnoBussinessException {

        if (updatedTurno == null) {
            LOGGER.error("El objeto turno a actualizar no puede ser nulo");
            throw new TurnoBussinessException("El objeto turno a actualizar no puede ser nulo");
        }

        if (updatedTurno.getPaciente() == null) {
            LOGGER.error("El campo 'pacienteId' del turno no puede ser nulo");
            throw new TurnoBussinessException("El campo 'paciente' del turno no puede ser nulo");
        }

        if (updatedTurno.getOdontologo() == null) {
            LOGGER.error("El campo 'odontologoId' del turno no puede ser nulo");
            throw new TurnoBussinessException("El campo 'odontologo' del turno no puede ser nulo");
        }

        if (updatedTurno.getFecha() == null || updatedTurno.getFecha().isEmpty()) {
            LOGGER.error("El campo 'fecha' del turno no puede ser nulo o vacío");
            throw new TurnoBussinessException("El campo 'fecha' del turno no puede ser nulo o vacío");
        }

        if (updatedTurno.getHora() == null || updatedTurno.getHora().isEmpty()) {
            LOGGER.error("El campo 'hora' del turno no puede ser nulo o vacío");
            throw new TurnoBussinessException("El campo 'hora' del turno no puede ser nulo o vacío");
        }

        Turno turno = turnoRepository.findById(id).orElse(null);

        if (turno == null) {
            LOGGER.error("No se encontró el turno con el ID proporcionado");
            throw new TurnoBussinessException("No se encontró el turno con el ID proporcionado");
        }

        Paciente paciente = new Paciente();
        paciente.setId(updatedTurno.getPaciente());
        Odontologo odontologo = new Odontologo();
        odontologo.setId(updatedTurno.getOdontologo());

        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFecha(updatedTurno.getFecha());
        turno.setHora(updatedTurno.getHora());

        TurnoResponseDto turnoResponseDto = new TurnoResponseDto();

        turnoResponseDto.setOdontologo(turno.getOdontologo().getId());
        turnoResponseDto.setPaciente(turno.getPaciente().getId());
        turnoResponseDto.setFecha(turno.getFecha());
        turnoResponseDto.setHora(turno.getHora());

        LOGGER.info("Se modificó correctamente el turno en la capa de servicio");
        return turnoResponseDto;
    }

    public TurnoDto buscarTurno(Long id) throws TurnoBussinessException {

        if(id == null) {
            LOGGER.error("El ID del turno no puede ser nulo.");
            throw new TurnoBussinessException("El ID del turno no puede ser nulo.");
        }

        Turno turno = turnoRepository.findById(id).orElse(null);

        if(turno == null) {
            LOGGER.error("No se encontró ningún turno con el ID proporcionado.");
            throw new TurnoBussinessException("No se encontró ningún turno con el ID proporcionado.");
        }

        if(turno.getPaciente() == null) {
            LOGGER.error("No se pudo encontrar al paciente del turno.");
            throw new TurnoBussinessException("No se pudo encontrar al paciente del turno.");
        }
        if(turno.getOdontologo() == null) {
            LOGGER.error("No se pudo encontrar al odontólogo del turno.");
            throw new TurnoBussinessException("No se pudo encontrar al odontólogo del turno.");
        }


        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("Se encontró correctamente el turno en la capa de servicio");
        return objectMapper.convertValue(turno, TurnoDto.class);
    }

    public void eliminarTurno(Long id) throws TurnoBussinessException {

        if (id == null || id < 0) {
            LOGGER.error("El ID del turno no puede ser nulo o negativo");
            throw new TurnoBussinessException("El ID del turno no puede ser nulo o negativo");
        }

        Turno turno = turnoRepository.findById(id).orElse(null);

        if(turno == null){
            LOGGER.error("No se encontró el turno con el ID especificado");
            throw new TurnoBussinessException("No se encontró el turno con el ID especificado");
        }

        turnoRepository.deleteById(id);
        LOGGER.info("Se eliminó correctamente el turno en la capa de servicio");
    }

    public Set<TurnoOdontologoDto> obtenerTurnoOdontologo(Long id){
        Set<Turno> turnos = turnoRepository.obtenerTurnosOdontologo(id);

        Set<TurnoOdontologoDto> turnoDtos = new HashSet<>();

        ObjectMapper objectMapper = new ObjectMapper();

        for(Turno turno : turnos){

            TurnoOdontologoDto turnoOdontologoDto = objectMapper.convertValue(turno, TurnoOdontologoDto.class);

            turnoDtos.add(turnoOdontologoDto);

        }

        return turnoDtos;
    }

    public Set<TurnoPacienteDto> obtenerTurnoPaciente(Long id){

        Set<Turno> turnos = turnoRepository.obtenerTurnosPaciente(id);

        Set<TurnoPacienteDto> turnoDtos = new HashSet<>();

        ObjectMapper objectMapper = new ObjectMapper();

        for(Turno turno : turnos){

            TurnoPacienteDto turnoPacienteDto = objectMapper.convertValue(turno, TurnoPacienteDto.class);

            turnoDtos.add(turnoPacienteDto);

        }

        return turnoDtos;
    }
}
