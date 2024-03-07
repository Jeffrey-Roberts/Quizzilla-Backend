package org.example.quizzilla_backend.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// https://hyperskill.org/learn/step/32430#initial-setup
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(Customizer.withDefaults())     // Default Basic auth config
                .csrf(configurer -> configurer.disable()) // for POST requests via Postman
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.PUT, "/term/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/term/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/terms").permitAll()
                        .requestMatchers(HttpMethod.POST, "/term").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}