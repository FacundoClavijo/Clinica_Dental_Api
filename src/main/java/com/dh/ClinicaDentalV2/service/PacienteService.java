package com.dh.ClinicaDentalV2.service;




import com.dh.ClinicaDentalV2.dto.*;
import com.dh.ClinicaDentalV2.entity.Domicilio;
import com.dh.ClinicaDentalV2.exception.DomicilioBusinessException;
import com.dh.ClinicaDentalV2.exception.PacienteBussinessException;
import com.dh.ClinicaDentalV2.exception.PacienteRepositoryException;
import com.dh.ClinicaDentalV2.entity.Paciente;
import com.dh.ClinicaDentalV2.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private DomicilioService domicilioService;

    @Autowired
    private TurnoService turnoService;

    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);
    public PacienteDto agregarPaciente(PacienteRequestDto pacienteRequest) throws PacienteBussinessException, PacienteRepositoryException {
        try {
            if (pacienteRequest == null) {
                LOGGER.error("El paciente es nulo");
                throw new PacienteBussinessException("El paciente es nulo");
            }
            if (pacienteRequest.getNombre() == null || pacienteRequest.getNombre().isEmpty()) {
                LOGGER.error("El nombre del paciente no puede estar vacío o ser nulo");
                throw new PacienteBussinessException("El nombre del paciente no puede estar vacío o ser nulo");
            }

            if (pacienteRequest.getApellido() == null || pacienteRequest.getApellido().isEmpty()) {
                LOGGER.error("El apellido del paciente no puede estar vacío o ser nulo");
                throw new PacienteBussinessException("El apellido del paciente no puede estar vacío o ser nulo");
            }

            if (pacienteRequest.getDni() == null || pacienteRequest.getDni().isEmpty()) {
                LOGGER.error("El DNI del paciente no puede estar vacío o ser nulo");
                throw new PacienteBussinessException("El DNI del paciente no puede estar vacío o ser nulo");
            }

            if (pacienteRequest.getDni().length() != 8) {
                LOGGER.error("El DNI del paciente debe tener 8 números");
                throw new PacienteBussinessException("El DNI del paciente debe tener 8 números");
            }

            if (pacienteRequest.getFechaAlta() == null || pacienteRequest.getFechaAlta().isEmpty()) {
                LOGGER.error("La fecha de alta del paciente no puede estar vacía o ser nula");
                throw new PacienteBussinessException("La fecha de alta del paciente no puede estar vacía o ser nula");
            }


            Paciente pacienteByDni = pacienteRepository.findByDni(pacienteRequest.getDni());

            if (pacienteByDni != null) {
                LOGGER.error("El dni proporcionado ya está en uso");
                throw new PacienteRepositoryException("El dni proporcionado ya está en uso");
            }

            ObjectMapper objectMapper = new ObjectMapper();

            Domicilio domicilioPaciente = domicilioService.agregarDomicilio(pacienteRequest.getDomicilio());

            Paciente paciente = objectMapper.convertValue(pacienteRequest,Paciente.class);
            paciente.setDomicilio(domicilioPaciente);
            Paciente pacienteRegistrado = pacienteRepository.save(paciente);

            PacienteDto pacienteDto = objectMapper.convertValue(pacienteRegistrado, PacienteDto.class);
            DomicilioDto domicilioDto= objectMapper.convertValue(domicilioPaciente,DomicilioDto.class);
            pacienteDto.setDomicilio(domicilioDto);

            LOGGER.info("Se agregó correctamente el paciente en la capa de servicio");
            return pacienteDto;

        } catch (DomicilioBusinessException e) {
            LOGGER.error("El paciente debe tener un domicilio registrado");
            throw new PacienteBussinessException("El paciente debe tener un domicilio registrado");
        }
    }

    public Set<PacienteResponseDto> listarPacientes() {

        ObjectMapper objectMapper = new ObjectMapper();
        List<Paciente> pacientes = pacienteRepository.findAll();
        Set<PacienteResponseDto> pacienteDtos = new HashSet<>();

        for (Paciente paciente : pacientes) {

            Set<TurnoPacienteDto> turnosDto = turnoService.obtenerTurnoPaciente(paciente.getId());
            PacienteResponseDto pacienteResponseDto = objectMapper.convertValue(paciente, PacienteResponseDto.class);

            pacienteResponseDto.setTurnos(turnosDto);

            pacienteDtos.add(pacienteResponseDto);
        }
        LOGGER.info("Se listaron correctamente los pacientes en la capa de servicio");
        return pacienteDtos;
    }

    @Transactional
    public PacienteDto modificarPaciente(Long id, PacienteRequestDto updatedPaciente) throws PacienteBussinessException, PacienteRepositoryException, DomicilioBusinessException {

            if (updatedPaciente == null) {
                LOGGER.error("El objeto paciente a actualizar no puede ser nulo");
                throw new PacienteBussinessException("El objeto paciente a actualizar no puede ser nulo");
            }

            if (updatedPaciente.getNombre() == null || updatedPaciente.getNombre().isEmpty()) {
                LOGGER.error("El campo 'nombre' del paciente no puede ser nulo o vacío");
                throw new PacienteBussinessException("El campo 'nombre' del paciente no puede ser nulo o vacío");
            }

            if (updatedPaciente.getApellido() == null || updatedPaciente.getApellido().isEmpty()) {
                LOGGER.error("El campo 'apellido' del paciente no puede ser nulo o vacío");
                throw new PacienteBussinessException("El campo 'apellido' del paciente no puede ser nulo o vacío");
            }

            if (updatedPaciente.getDni() == null || updatedPaciente.getDni().isEmpty()) {
                LOGGER.error("El campo 'dni' del paciente no puede ser nulo o vacío");
                throw new PacienteBussinessException("El campo 'dni' del paciente no puede ser nulo o vacío");
            }

            Paciente paciente = pacienteRepository.findById(id).orElse(null);

            if (paciente == null) {
                LOGGER.error("No se encontró el paciente con el ID proporcionado");
                throw new PacienteBussinessException("No se encontró el paciente con el ID proporcionado");
            }

            if (!paciente.getDni().equals(updatedPaciente.getDni())) {
                Paciente pacienteByDni = pacienteRepository.findByDni(updatedPaciente.getDni());

                if (pacienteByDni != null) {
                    LOGGER.error("El dni proporcionado ya está en uso");
                    throw new PacienteRepositoryException("El dni proporcionado ya está en uso");
                }
            }

            DomicilioDto domicilioDto = domicilioService.modificarDomicilio(id,updatedPaciente.getDomicilio());

            paciente.setNombre(updatedPaciente.getNombre());
            paciente.setApellido(updatedPaciente.getApellido());
            paciente.setDni(updatedPaciente.getDni());
            paciente.setFechaAlta(updatedPaciente.getFechaAlta());

            ObjectMapper objectMapper = new ObjectMapper();

            PacienteDto pacienteDto = objectMapper.convertValue(paciente, PacienteDto.class);

            pacienteDto.setDomicilio(domicilioDto);

            LOGGER.info("Se modificó correctamente el paciente en la capa de servicio");
            return pacienteDto;


    }

    public PacienteDto buscarPaciente(Long id) throws PacienteBussinessException {

        if (id == null || id < 0) {
            LOGGER.error("El ID del paciente no puede ser nulo o negativo");
            throw new PacienteBussinessException("El ID del paciente no puede ser nulo o negativo");
        }

        Paciente paciente = pacienteRepository.findById(id).orElse(null);

        if (paciente == null) {
            LOGGER.error("No se encontró el paciente con el ID especificado");
            throw new PacienteBussinessException("No se encontró el paciente con el ID especificado");
        }

        Set<TurnoPacienteDto> turnosDto = turnoService.obtenerTurnoPaciente(id);

        ObjectMapper objectMapper = new ObjectMapper();

        PacienteDto pacienteDto = objectMapper.convertValue(paciente, PacienteDto.class);

        pacienteDto.setTurnos(turnosDto);
        LOGGER.info("Se encontró correctamente el paciente en la capa de servicio: " + id);
        return pacienteDto;
    }

    public void eliminarPaciente(Long id) throws PacienteBussinessException {

        if (id == null || id < 0) {
            LOGGER.error("El ID del paciente no puede ser nulo o negativo");
            throw new PacienteBussinessException("El ID del paciente no puede ser nulo o negativo");
        }

        Paciente paciente = pacienteRepository.findById(id).orElse(null);

        if(paciente == null){
            LOGGER.error("No se encontró el paciente con el ID especificado");
            throw new PacienteBussinessException("No se encontró el paciente con el ID especificado");
        }

        pacienteRepository.deleteById(id);
        LOGGER.info("Se eliminó correctamente el paciente en la capa de servicio: " + id);
    }
}
