package com.jrosa.myAmb.controller;

import com.jrosa.myAmb.constants.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

     @GetMapping(Constants.HOME)
     public String home(HttpServletRequest request){
         return Constants.HOME;
     }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
