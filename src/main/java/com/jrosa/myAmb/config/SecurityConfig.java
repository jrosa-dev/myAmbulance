package com.jrosa.myAmb.config;

import com.jrosa.myAmb.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.jrosa.myAmb.constants.Constants.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // VERY IMPORTANT without this @PreAuthorize doesn't work
public class SecurityConfig {

    private final MyUserDetailsService userDetailsService;

    // Add @Lazy here to break the circular dependency
    public SecurityConfig(@Lazy MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(MAPPING_LOGIN, MAPPING_SIGNUP,
                                "/css/**",
                                "/js/**",
                                "/assets/**",
                                "/images/**",
                                "/webjars/**").permitAll()
                        .anyRequest().authenticated() // If it isn't a login or sign up don't allow
                                                      // anything without being authenticated
                )
                .formLogin(form -> form
                        .loginPage(MAPPING_LOGIN)
                        .defaultSuccessUrl(MAPPING_HOME, true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl(MAPPING_LOGIN)
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }


    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}