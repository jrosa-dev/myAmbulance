package com.jrosa.myAmb.controller;

import com.jrosa.myAmb.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

import static com.jrosa.myAmb.constants.Constants.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MessageSource messageSource;

    @GetMapping(MAPPING_LOGIN)
    public String loginPage() {
        return "login";
    }

    @GetMapping(MAPPING_SIGNUP)
    public String signupPage() {
        return "signup";
    }

    @PostMapping(MAPPING_SIGNUP)
    public String signup(
            @RequestParam String username,
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam Long fire_department_id,
            @RequestParam Long internal_id,
            Locale locale,
            RedirectAttributes redirectAttributes
    ) {
        try {
            authService.register(username, name, password, fire_department_id, internal_id,locale);
            redirectAttributes.addFlashAttribute("success",
                    messageSource.getMessage(SUCCESS_ACCOUNT_CREATED, null, locale));
            return "redirect:"+MAPPING_LOGIN;
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:"+MAPPING_SIGNUP;
        }
    }
}
