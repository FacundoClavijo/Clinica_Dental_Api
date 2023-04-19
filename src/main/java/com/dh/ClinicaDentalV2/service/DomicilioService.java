package com.dh.ClinicaDentalV2.service;

import com.dh.ClinicaDentalV2.dto.DomicilioDto;
import com.dh.ClinicaDentalV2.dto.DomicilioRequestDto;
import com.dh.ClinicaDentalV2.exception.DomicilioBusinessException;
import com.dh.ClinicaDentalV2.entity.Domicilio;
import com.dh.ClinicaDentalV2.repository.DomicilioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DomicilioService {

    @Autowired
    private DomicilioRepository domicilioRepository;

    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);

    public Set<DomicilioDto> listarDomicilios(){

        List<Domicilio> domicilios = domicilioRepository.findAll();
        Set<DomicilioDto> domiciliosDtos = new HashSet<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for(Domicilio domicilio: domicilios){

            DomicilioDto domicilioDto = objectMapper.convertValue(domicilio, DomicilioDto.class);

            domiciliosDtos.add(domicilioDto);
        }
        LOGGER.info("Se listaron correctamente los domicilios en la capa de servicio");
        return domiciliosDtos;
    }

    public Domicilio agregarDomicilio(DomicilioRequestDto domicilioRequest) throws DomicilioBusinessException {

        if (domicilioRequest == null) {
            LOGGER.error("El domicilio es nulo");
            throw new DomicilioBusinessException("El domicilio es nulo");
        }

        if (domicilioRequest.getCalle() == null || domicilioRequest.getCalle().isEmpty()) {
            LOGGER.error("La calle no puede estar vacía o ser nula");
            throw new DomicilioBusinessException("La calle no puede estar vacía o ser nula");
        }

        if (domicilioRequest.getNumero() == null || String.valueOf(domicilioRequest.getNumero()).isEmpty()) {
            LOGGER.error("El número no puede estar vacío o ser nulo");
            throw new DomicilioBusinessException("El número no puede estar vacío o ser nulo");
        }

        if (domicilioRequest.getLocalidad() == null || domicilioRequest.getLocalidad().isEmpty()) {
            LOGGER.error("La localidad no puede estar vacía o ser nula");
            throw new DomicilioBusinessException("La localidad no puede estar vacía o ser nula");
        }

        if(domicilioRequest.getProvincia() == null || domicilioRequest.getProvincia().isEmpty()){
            LOGGER.error("La provincia no puede estar vacía o ser nula");
            throw new DomicilioBusinessException("La provincia no puede estar vacía o ser nula");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Domicilio domicilio = objectMapper.convertValue(domicilioRequest,Domicilio.class);
        Domicilio domicilioGuardado = domicilioRepository.save(domicilio);
        LOGGER.info("El domicilio se ha guardado correctamente en la capa de servicio");
        return domicilioGuardado;
    }

    public DomicilioDto buscarDomicilio(Long id) throws DomicilioBusinessException {
        if (id == null || id < 0) {
            LOGGER.error("El ID del domicilio no puede ser nulo o negativo");
            throw new DomicilioBusinessException("El ID del domicilio no puede ser nulo o negativo");
        }

        Domicilio domicilio = domicilioRepository.findById(id).orElse(null);
        if(domicilio == null){
            LOGGER.error("No se encontró el domicilio con el ID especificado");
            throw new DomicilioBusinessException("No se encontró el domicilio con el ID especificado");
        }

        ObjectMapper objectMapper = new ObjectMapper();

        LOGGER.info("Se encontró el domicilio en la capa de servicio: " + domicilio.getId());
        return objectMapper.convertValue(domicilio, DomicilioDto.class);
    }

    @Transactional
    public DomicilioDto modificarDomicilio( Long id, DomicilioRequestDto updatedDomicilio) throws DomicilioBusinessException {
        if (updatedDomicilio == null) {
            LOGGER.error("El objeto Domicilio a actualizar es nulo");
            throw new DomicilioBusinessException("El objeto Domicilio a actualizar es nulo");
        }

        Domicilio domicilio = domicilioRepository.findById(id).orElse(null);
        if(domicilio == null){
            LOGGER.error("No se encontró el domicilio con el ID especificado");
            throw new DomicilioBusinessException("No se encontró el domicilio con el ID especificado");
        }

        if (updatedDomicilio.getCalle() == null || updatedDomicilio.getCalle().isEmpty()) {
            LOGGER.error("El campo 'calle' del domicilio no puede ser nulo o vacío");
            throw new DomicilioBusinessException("El campo 'calle' del domicilio no puede ser nulo o vacío");
        }

        if (updatedDomicilio.getNumero() == null || String.valueOf(updatedDomicilio.getNumero()).isEmpty()) {
            LOGGER.error("El campo 'numero' del domicilio no puede ser nulo o vacío");
            throw new DomicilioBusinessException("El campo 'numero' del domicilio no puede ser nulo o vacío");
        }

        if (updatedDomicilio.getLocalidad() == null || updatedDomicilio.getLocalidad().isEmpty()) {
            LOGGER.error("El campo 'localidad' del domicilio no puede ser nulo o vacío");
            throw new DomicilioBusinessException("El campo 'localidad' del domicilio no puede ser nulo o vacío");
        }

        if (updatedDomicilio.getProvincia() == null || updatedDomicilio.getProvincia().isEmpty()) {
            LOGGER.error("El campo 'provincia' del domicilio no puede ser nulo o vacío");
            throw new DomicilioBusinessException("El campo 'provincia' del domicilio no puede ser nulo o vacío");
        }

        domicilio.setCalle(updatedDomicilio.getCalle());
        domicilio.setNumero(updatedDomicilio.getNumero());
        domicilio.setLocalidad(updatedDomicilio.getLocalidad());
        domicilio.setProvincia(updatedDomicilio.getProvincia());

        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("Se modificó el domicilio correctamente en la capa de servicio");
        return objectMapper.convertValue(domicilio, DomicilioDto.class);
    }

    public void eliminarDomicilio(Long id) throws DomicilioBusinessException {

        if (id == null || id < 0) {
            LOGGER.error("El ID del domicilio no puede ser nulo o negativo");
            throw new DomicilioBusinessException("El ID del domicilio no puede ser nulo o negativo");
        }

        Domicilio domicilio = domicilioRepository.findById(id).orElse(null);

        if(domicilio == null){
            LOGGER.error("No se encontró el domicilio con el ID especificado");
            throw new DomicilioBusinessException("No se encontró el domicilio con el ID especificado");
        }

        domicilioRepository.deleteById(id);
        LOGGER.info("Se borró correctamente el domicilio en la capa de servicio: " + id);
    }
}
