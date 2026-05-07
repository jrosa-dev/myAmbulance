package com.jrosa.myAmb.service;

import com.jrosa.myAmb.model.User;
import com.jrosa.myAmb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static com.jrosa.myAmb.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;


    public void register(String username, String name, String password,
                         Long fire_department_id, Long internal_id, Locale locale) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException(messageSource.getMessage(ERROR_USERNAME_TAKEN, null, locale));
        }
        // Username has to be at least 5 characters long and without special characters
        if (!username.matches("[a-zA-Z0-9]{5,}")) {
            throw new RuntimeException(messageSource.getMessage(ERROR_USERNAME_INVALID, null, locale));
        }


        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setFire_department_id(fire_department_id);
        user.setInternal_id(internal_id);
        user.setRole(DEFAULT_ROLE.name());

        userRepository.save(user);
    }
}
