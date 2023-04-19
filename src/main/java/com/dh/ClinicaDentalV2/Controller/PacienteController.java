package com.dh.ClinicaDentalV2.Controller;



import com.dh.ClinicaDentalV2.dto.PacienteDto;
import com.dh.ClinicaDentalV2.dto.PacienteRequestDto;
import com.dh.ClinicaDentalV2.dto.PacienteResponseDto;
import com.dh.ClinicaDentalV2.exception.DomicilioBusinessException;
import com.dh.ClinicaDentalV2.exception.PacienteBussinessException;
import com.dh.ClinicaDentalV2.exception.PacienteRepositoryException;
import com.dh.ClinicaDentalV2.service.OdontologoService;
import com.dh.ClinicaDentalV2.service.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);

    @RequestMapping(value="/registrar-paciente",method=RequestMethod.GET)
    public ModelAndView pacientesVista() {
        ModelAndView mav = new ModelAndView("registro-paciente");
        return mav;
    }

    @RequestMapping(value="/buscar-paciente",method=RequestMethod.GET)
    public ModelAndView pacientesBuscarVista() {
        ModelAndView mav = new ModelAndView("buscar-paciente");
        return mav;
    }

    @RequestMapping(value="/listar-pacientes",method=RequestMethod.GET)
    public ModelAndView listarPacientesVista(Model model) {
        Set<PacienteResponseDto> lista =  listarPacientes().getBody();
        List<PacienteResponseDto> listaOrdenada = new ArrayList<>(lista);
        listaOrdenada.sort(Comparator.comparing(PacienteResponseDto::getId));
        model.addAttribute("pacientes", listaOrdenada);
        ModelAndView mav = new ModelAndView("lista-pacientes");
        return mav;
    }

    @RequestMapping(value="/actualizar-paciente/{id}", method=RequestMethod.GET)
    public ModelAndView actualizarVista(@PathVariable("id") Long id) {
        PacienteDto paciente = buscarPaciente(id).getBody();
        ModelAndView mav = new ModelAndView("actualizar-paciente");
        mav.addObject("paciente", paciente);
        return mav;
    }


    @GetMapping
    public ResponseEntity<Set<PacienteResponseDto>> listarPacientes(){
        ResponseEntity response;

        try {
            response = ResponseEntity.status(200).body(pacienteService.listarPacientes());
            LOGGER.info("Se listaron correctamente los pacientes");
        } catch (Exception e) {
            response = ResponseEntity.internalServerError().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de paciente: " + e.getMessage());
        }
        return response;

    }

    @GetMapping("/buscar-paciente/{id}")
    public ResponseEntity<PacienteDto> buscarPaciente(@PathVariable Long id){
        ResponseEntity response;
        try {
            response = ResponseEntity.status(200).body(pacienteService.buscarPaciente(id));
            LOGGER.info("Se encontró correctamente el paciente: " + id);
        } catch (PacienteBussinessException e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de paciente: " + e.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.internalServerError().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de paciente: " + e.getMessage());
        }

        return response;
    }

    @RequestMapping(value="/registrar-paciente",method=RequestMethod.POST)
    public ResponseEntity<PacienteDto> agregarPaciente(@RequestBody PacienteRequestDto paciente){
        ResponseEntity response;

        try {
            response = ResponseEntity.status(201).body(pacienteService.agregarPaciente(paciente));
            LOGGER.info("Se agregó correctamente el paciente: " + paciente.getNombre());
        } catch (PacienteBussinessException e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de paciente: " + e.getMessage());
        } catch (PacienteRepositoryException e) {
            response = ResponseEntity.internalServerError().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de paciente: " + e.getMessage());
        }

        return response;
    }

    @PutMapping("/actualizar-paciente/{id}")
    public ResponseEntity<PacienteDto> modificarPaciente( @PathVariable Long id,@RequestBody PacienteRequestDto paciente){
        ResponseEntity response;

        try {
            response = ResponseEntity.status(200).body(pacienteService.modificarPaciente(id,paciente));
            LOGGER.info("Se modificó correctamente el paciente: " + id);
        } catch (PacienteBussinessException | DomicilioBusinessException e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de paciente: " + e.getMessage());
        } catch (PacienteRepositoryException e) {
            response = ResponseEntity.internalServerError().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de paciente: " + e.getMessage());
        }

        return response;
    }

    @DeleteMapping("/eliminar-paciente/{id}")
    public ResponseEntity<PacienteDto> eliminarPaciente(@PathVariable Long id){

        ResponseEntity response;

        try {
            pacienteService.eliminarPaciente(id);
            response = ResponseEntity.noContent().build();
            LOGGER.info("Se eliminó correctamente el paciente: " + id);
        } catch (PacienteBussinessException e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de paciente: " + e.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.internalServerError().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de paciente: " + e.getMessage());
        }
        return response;

    }

}
