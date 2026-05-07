package com.jrosa.myAmb.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.jrosa.myAmb.constants.Constants.MAPPING_HOME;

@Controller
public class HomeController {

    @GetMapping("/")
    public String defaultPage(HttpServletRequest request){
        return MAPPING_HOME;
    }

    @GetMapping(MAPPING_HOME)
    public String home(HttpServletRequest request){
        return MAPPING_HOME;
    }

}
