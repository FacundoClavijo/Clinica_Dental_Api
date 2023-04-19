package com.dh.ClinicaDentalV2.Controller;




import com.dh.ClinicaDentalV2.dto.*;
import com.dh.ClinicaDentalV2.exception.OdontologoBussinessException;
import com.dh.ClinicaDentalV2.exception.PacienteBussinessException;
import com.dh.ClinicaDentalV2.exception.TurnoBussinessException;
import com.dh.ClinicaDentalV2.service.OdontologoService;
import com.dh.ClinicaDentalV2.service.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.*;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private TurnoService turnoService;

    private static final Logger LOGGER = Logger.getLogger(OdontologoService.class);

    @RequestMapping(value="/registrar-turno",method=RequestMethod.GET)
    public ModelAndView turnosVista() {
        ModelAndView mav = new ModelAndView("registrar-turno");
        return mav;
    }

    @RequestMapping(value="/buscar-turno",method=RequestMethod.GET)
    public ModelAndView turnosBuscarVista() {
        ModelAndView mav = new ModelAndView("buscar-turno");
        return mav;
    }

    @RequestMapping(value="/listar-turnos",method=RequestMethod.GET)
    public ModelAndView listarTurnosVista(Model model) {
        Set<TurnoDto> lista =  listarTurnos().getBody();
        List<TurnoDto> listaOrdenada = new ArrayList<>(lista);
        listaOrdenada.sort(Comparator.comparing(TurnoDto::getId));
        model.addAttribute("turnos", listaOrdenada);
        ModelAndView mav = new ModelAndView("lista-turnos");
        return mav;
    }

    @RequestMapping(value="/actualizar-turno/{id}", method=RequestMethod.GET)
    public ModelAndView actualizarVista(@PathVariable("id") Long id) {
        TurnoDto turno = buscarTurno(id).getBody();
        ModelAndView mav = new ModelAndView("actualizar-turno");
        mav.addObject("turno", turno);
        return mav;
    }

    @GetMapping
    public ResponseEntity<Set<TurnoDto>> listarTurnos(){

        ResponseEntity response;

        try {
            response = ResponseEntity.status(200).body(turnoService.listarTurnos());
            LOGGER.info("Se listaron correctamente los turnos");
        } catch (Exception e) {
            response = ResponseEntity.internalServerError().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de turno: " + e.getMessage());
        }

        return response;
    }

    @GetMapping("/buscar-turno/{id}")
    public ResponseEntity<TurnoDto> buscarTurno(@PathVariable Long id){
        ResponseEntity response;
        try {
            response = ResponseEntity.status(200).body(turnoService.buscarTurno(id));
            LOGGER.info("Se encontró correctamente el turno: " + id);
        } catch (TurnoBussinessException e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de turno: " + e.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.internalServerError().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de turno: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/registrar-turno")
    public ResponseEntity<TurnoResponseDto> agregarTurno(@RequestBody TurnoRequestDto turno){

        ResponseEntity response;

        try {
            response = ResponseEntity.status(201).body(turnoService.agregarTurno(turno));
            LOGGER.info("Se agregó correctamente el turno");
        } catch (TurnoBussinessException | PacienteBussinessException | OdontologoBussinessException e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de turno: " + e.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.internalServerError().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de turno: " + e.getMessage());
        }
        return response;
    }

    @PutMapping("/actualizar-turno/{id}")
    public ResponseEntity<TurnoDto> modificarTurno( @PathVariable Long id,@RequestBody TurnoRequestDto turno){
        ResponseEntity response;

        try {
            response = ResponseEntity.status(200).body(turnoService.modificarTurno(id,turno));
            LOGGER.info("Se modificó correctamente el turno: " + id);
        } catch (TurnoBussinessException e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de turno: " + e.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.internalServerError().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de turno: " + e.getMessage());
        }

        return response;

    }

    @DeleteMapping("/eliminar-turno/{id}")
    public ResponseEntity<TurnoDto> eliminarTurno(@PathVariable Long id){

        ResponseEntity response;

        try {
            turnoService.eliminarTurno(id);
            response = ResponseEntity.noContent().build();
            LOGGER.info("Se eliminó correctamente el turno");
        } catch (TurnoBussinessException e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de turno: " + e.getMessage());
        } catch (Exception e) {
            response = ResponseEntity.internalServerError().body(e.getMessage());
            LOGGER.error("Hubo el siguiente error en la capa de controller de turno: " + e.getMessage());
        }
        return response;

    }
}
