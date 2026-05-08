package com.jrosa.myAmb.controller;

import com.jrosa.myAmb.constants.Constants;
import com.jrosa.myAmb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Locale;

import static com.jrosa.myAmb.constants.Constants.*;
import static com.jrosa.myAmb.constants.Constants.MAPPING_MANAGE_USERS;
import static com.jrosa.myAmb.constants.Constants.ROLES;

@Controller
@RequiredArgsConstructor
public class RoleController {

    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping(MAPPING_MANAGE_USERS)
    @PreAuthorize("hasRole('COMMAND')")
    public String manageUsers(Model model, @AuthenticationPrincipal UserDetails userDetails){
        // DTO used here as a good practice, since only the Commander will have access to this page
        model.addAttribute(FIRE_DEPT_USERS, userService.getFireDepartmentUsersByUsername(userDetails.getUsername()));
        model.addAttribute(ROLES, Arrays.stream(Constants.RoleName.values()).map(Enum::name).toList());
        return MAPPING_MANAGE_USERS;
    }

    @PostMapping(MAPPING_MANAGE_USER_ROLE)
    @PreAuthorize("hasRole('COMMAND')")
    public String manageUserRole(
            @RequestParam String username,
            @RequestParam String role,
            Locale locale,
            RedirectAttributes redirectAttributes){
        try {
            if (role.equals(Constants.RoleName.ROLE_COMMAND.name())) {
                throw new RuntimeException("Command Role cannot be assigned here");
            }
            userService.setUserRoleByUsername(username, role);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:"+MAPPING_HOME;
        }

        redirectAttributes.addFlashAttribute("success", messageSource.getMessage(SUCCESS_ROLE_UPDATED, null, locale));
        return "redirect:"+MAPPING_MANAGE_USERS;
    }
}
