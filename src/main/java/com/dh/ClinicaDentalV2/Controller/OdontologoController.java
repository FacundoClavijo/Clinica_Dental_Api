package com.dh.ClinicaDentalV2.Controller;


import com.dh.ClinicaDentalV2.dto.OdontologoRequestDto;
import com.dh.ClinicaDentalV2.dto.OdontologoResponseDto;
import com.dh.ClinicaDentalV2.exception.OdontologoBussinessException;
import com.dh.ClinicaDentalV2.exception.OdontologoRepositoryException;
import com.dh.ClinicaDentalV2.service.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    @Autowired
    private OdontologoService odontologoService;

    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);

    @RequestMapping(value="/registrar-odontologo",method=RequestMethod.GET)
    public ModelAndView odontologosVista() {
        ModelAndView mav = new ModelAndView("registro-odontologo");
        return mav;
    }
    @RequestMapping(value="/buscar-odontologo",method=RequestMethod.GET)
    public ModelAndView odontologosBuscarVista() {
        ModelAndView mav = new ModelAndView("buscar-odontologo");
        return mav;
    }

    @RequestMapping(value="/listar-odontologos",method=RequestMethod.GET)
    public ModelAndView listarOdontologosVista(Model model) {
        Set<OdontologoResponseDto> lista =  listarOdontologos().getBody();
        List<OdontologoResponseDto> listaOrdenada = new ArrayList<>(lista);
        listaOrdenada.sort(Comparator.comparing(OdontologoResponseDto::getId));
        model.addAttribute("odontologos", listaOrdenada);

        ModelAndView mav = new ModelAndView("lista-odontologos");
        return mav;
    }

    @RequestMapping(value="/actualizar-odontologo/{id}", method=RequestMethod.GET)
    public ModelAndView actualizarVista(@PathVariable("id") Long id) {
        OdontologoResponseDto odontologo = buscarOdontologo(id).getBody();
        ModelAndView mav = new ModelAndView("actualizar-odontologo");
        mav.addObject("odontologo", odontologo);
        return mav;
    }

    @GetMapping
    public ResponseEntity<Set<OdontologoResponseDto>> listarOdontologos(){

        ResponseEntity response;

        try {
            response = ResponseEntity.status(200).body(odontologoService.listarOdontologos());
            LOGGER.info("Se listaron correctamente los odontologos");
        } catch (Exception e) {
            response = ResponseEntity.internalServerError().build();
            LOGGER.error("Hubo el siguiente error en la capa de controller de odontologo: " + e.getMessage());
        }

        return response;
    }

    @GetMapping("/buscar-odontologo/{id}")
    public ResponseEntity<OdontologoResponseDto> buscarOdontologo(@PathVariable Long id){

        ResponseEntity response;
        try {
            response = ResponseEntity.status(200).body(odontologoService.buscarOdontologo(id));
            LOGGER.info("Se encontró correctamente el odontologo: " + id);
        } catch (OdontologoBussinessException e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de odontologo: " + e.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.internalServerError().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de odontologo: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/registrar-odontologo")
    public ResponseEntity<OdontologoResponseDto> agregarOdontologo(@RequestBody OdontologoRequestDto odontologo){

        ResponseEntity response;

        try {
            response = ResponseEntity.status(201).body(odontologoService.agregarOdontologo(odontologo));
            LOGGER.info("Se agregó correctamente el odontologo: " + odontologo.getNombre());
        } catch (OdontologoBussinessException e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de odontologo: " + e.getMessage());
        } catch (OdontologoRepositoryException e) {
            response = ResponseEntity.internalServerError().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de odontologo: " + e.getMessage());
        }
        return response;
    }

    @PutMapping("/actualizar-odontologo/{id}")
    public ResponseEntity<OdontologoResponseDto> modificarOdontologo( @PathVariable Long id,@RequestBody OdontologoRequestDto odontologo){

        ResponseEntity response;

        try {
            response = ResponseEntity.status(200).body(odontologoService.modificarOdontologo(id,odontologo));
            LOGGER.info("Se modificó correctamente el odontologo: " + id);
        } catch (OdontologoBussinessException e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de odontologo: " + e.getMessage());
        }
         catch (OdontologoRepositoryException e) {
            response = ResponseEntity.internalServerError().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de odontologo: " + e.getMessage());
        }

        return response;
    }

    @DeleteMapping("/eliminar-odontologo/{id}")
    public ResponseEntity<OdontologoResponseDto> eliminarOdontologo(@PathVariable Long id){

        ResponseEntity response;

        try {
            odontologoService.eliminarOdontologo(id);
            response = ResponseEntity.noContent().build();
            LOGGER.info("Se eliminó correctamente el odontologo: " + id);
        } catch (OdontologoBussinessException e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de odontologo: " + e.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.internalServerError().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de odontologo: " + e.getMessage());
        }
        return response;

    }
}
