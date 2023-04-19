package com.dh.ClinicaDentalV2.service;


import com.dh.ClinicaDentalV2.dto.*;
import com.dh.ClinicaDentalV2.exception.OdontologoBussinessException;
import com.dh.ClinicaDentalV2.exception.OdontologoRepositoryException;
import com.dh.ClinicaDentalV2.entity.Odontologo;
import com.dh.ClinicaDentalV2.repository.OdontologoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class OdontologoService {

    @Autowired
    private OdontologoRepository odontologoRepository;

    @Autowired
    private TurnoService turnoService;

    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);

    public Set<OdontologoResponseDto> listarOdontologos(){

        List<Odontologo> odontologos = odontologoRepository.findAll();

        Set<OdontologoResponseDto> odontologoDtos = new HashSet<>();

        ObjectMapper objectMapper = new ObjectMapper();

        for(Odontologo odontologo : odontologos) {

            Set<TurnoOdontologoDto> turnosDto = turnoService.obtenerTurnoOdontologo(odontologo.getId());
            OdontologoResponseDto odontologoResponseDto = objectMapper.convertValue(odontologo, OdontologoResponseDto.class);

            odontologoResponseDto.setTurnos(turnosDto);

            odontologoDtos.add(odontologoResponseDto);

        }
        LOGGER.info("Se listaron correctamente los odontólogos en la capa de servicio");
        return odontologoDtos;
    }

    public OdontologoResponseDto agregarOdontologo(OdontologoRequestDto odontologoRequest) throws OdontologoBussinessException, OdontologoRepositoryException {

            if (odontologoRequest == null) {
                LOGGER.error("El odontologo es nulo");
                throw new OdontologoBussinessException("El odontologo es nulo");
            }
            if (odontologoRequest.getNombre() == null || odontologoRequest.getNombre().isEmpty()) {
                LOGGER.error("El nombre del odontólogo no puede ser nulo o vacío");
                throw new OdontologoBussinessException("El nombre del odontólogo no puede ser nulo o vacío");
            }

            if (odontologoRequest.getApellido() == null || odontologoRequest.getApellido().isEmpty()) {
                LOGGER.error("El apellido del odontólogo no puede ser nulo o vacío");
                throw new OdontologoBussinessException("El apellido del odontólogo no puede ser nulo o vacío");
            }

            if (odontologoRequest.getNroMatricula() == null || String.valueOf(odontologoRequest.getNroMatricula()).isEmpty()) {
                LOGGER.error("El número de matrícula del odontólogo no puede ser nulo o vacío");
                throw new OdontologoBussinessException("El número de matrícula del odontólogo no puede ser nulo o vacío");
            }

            Odontologo odontologoByNroMatricula = odontologoRepository.findByNroMatricula(odontologoRequest.getNroMatricula());

            if (odontologoByNroMatricula != null) {
                LOGGER.error("El número de matrícula proporcionado ya está en uso");
                throw new OdontologoRepositoryException("El número de matrícula proporcionado ya está en uso");
            }

            ObjectMapper objectMapper = new ObjectMapper();

            Odontologo odontologo = objectMapper.convertValue(odontologoRequest, Odontologo.class);

            Odontologo odontologoRegistrado = odontologoRepository.save(odontologo);
            Set<TurnoOdontologoDto> turnosOdontologoDto = turnoService.obtenerTurnoOdontologo(odontologoRegistrado.getId());
            OdontologoResponseDto odontologoResponseDto = objectMapper.convertValue(odontologoRegistrado, OdontologoResponseDto.class);
            odontologoResponseDto.setTurnos(turnosOdontologoDto);

            LOGGER.info("Se agregó correctamente el odontologo en la capa de servicio");
            return odontologoResponseDto;
        }

    @Transactional
    public OdontologoResponseDto modificarOdontologo(Long id, OdontologoRequestDto updatedOdontologo) throws OdontologoBussinessException, OdontologoRepositoryException {

            if (updatedOdontologo == null) {
                LOGGER.error("El objeto odontólogo a actualizar no puede ser nulo");
                throw new OdontologoBussinessException("El objeto odontólogo a actualizar no puede ser nulo");
            }

            if (updatedOdontologo.getNombre() == null || updatedOdontologo.getNombre().isEmpty()) {
                LOGGER.error("El campo 'nombre' del odontólogo no puede ser nulo o vacío");
                throw new OdontologoBussinessException("El campo 'nombre' del odontólogo no puede ser nulo o vacío");
            }

            if (updatedOdontologo.getApellido() == null || updatedOdontologo.getApellido().isEmpty()) {
                LOGGER.error("El campo 'apellido' del odontólogo no puede ser nulo o vacío");
                throw new OdontologoBussinessException("El campo 'apellido' del odontólogo no puede ser nulo o vacío");
            }

            if (updatedOdontologo.getNroMatricula() == null ||String.valueOf(updatedOdontologo.getNroMatricula()).isEmpty()) {
                LOGGER.error("El campo 'nroMatricula' del odontólogo no puede ser nulo o vacío");
                throw new OdontologoBussinessException("El campo 'nroMatricula' del odontólogo no puede ser nulo o vacío");
            }

            Odontologo odontologo = odontologoRepository.findById(id).orElse(null);

            if (odontologo == null) {
                LOGGER.error("No se encontró el odontólogo con el ID proporcionado");
                throw new OdontologoBussinessException("No se encontró el odontólogo con el ID proporcionado");
            }

            if (!odontologo.getNroMatricula().equals(updatedOdontologo.getNroMatricula())) {
                Odontologo odontologoByNroMatricula = odontologoRepository.findByNroMatricula(updatedOdontologo.getNroMatricula());

                if (odontologoByNroMatricula != null) {
                    LOGGER.error("El número de matrícula proporcionado ya está en uso");
                    throw new OdontologoRepositoryException("El número de matrícula proporcionado ya está en uso");
                }
            }

            odontologo.setNombre(updatedOdontologo.getNombre());
            odontologo.setApellido(updatedOdontologo.getApellido());
            odontologo.setNroMatricula(updatedOdontologo.getNroMatricula());

            ObjectMapper objectMapper = new ObjectMapper();

            Set<TurnoOdontologoDto> turnosOdontologoDto = turnoService.obtenerTurnoOdontologo(odontologo.getId());

            OdontologoResponseDto odontologoResponseDto = objectMapper.convertValue(odontologo, OdontologoResponseDto.class);
            odontologoResponseDto.setTurnos(turnosOdontologoDto);

            LOGGER.info("Se modificó correctamente el odontólogo en la capa de servicio");
            return odontologoResponseDto;
    }

    public OdontologoResponseDto buscarOdontologo(Long id) throws OdontologoBussinessException {
        if (id == null || id < 0) {
            LOGGER.error("El ID del odontólogo no puede ser nulo o negativo");
            throw new OdontologoBussinessException("El ID del odontólogo no puede ser nulo o negativo");
        }

        Odontologo odontologo = odontologoRepository.findById(id).orElse(null);

        if (odontologo == null) {
            LOGGER.error("No se encontró el odontólogo con el ID especificado");
            throw new OdontologoBussinessException("No se encontró el odontólogo con el ID especificado");
        }

        Set<TurnoOdontologoDto> turnosDto = turnoService.obtenerTurnoOdontologo(id);

        ObjectMapper objectMapper = new ObjectMapper();

        OdontologoResponseDto odontologoResponseDto = objectMapper.convertValue(odontologo, OdontologoResponseDto.class);

        odontologoResponseDto.setTurnos(turnosDto);
        LOGGER.info("Se encontró correctamente el odontólogo en la capa de servicio: " + id);
        return odontologoResponseDto;

    }

    public void eliminarOdontologo(Long id) throws OdontologoBussinessException {

        if (id == null || id < 0) {
            LOGGER.error("El ID del odontólogo no puede ser nulo o negativo");
            throw new OdontologoBussinessException("El ID del odontólogo no puede ser nulo o negativo");
        }

        Odontologo odontologo = odontologoRepository.findById(id).orElse(null);

        if(odontologo == null){
            LOGGER.error("No se encontró el odontólogo con el ID especificado");
            throw new OdontologoBussinessException("No se encontró el odontólogo con el ID especificado");
        }
        odontologoRepository.deleteById(id);
        LOGGER.info("Se eliminó correctamente el odontólogo en la capa de servicio: " + id);
    }
}
