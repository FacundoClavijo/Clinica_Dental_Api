package com.dh.ClinicaDentalV2.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
@RestController
public class IndexController {
    @RequestMapping(value="/index",method= RequestMethod.GET)
    public ModelAndView indexVista() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
}
