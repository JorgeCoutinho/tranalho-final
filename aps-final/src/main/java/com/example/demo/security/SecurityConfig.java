package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        var userDetailsManager = new InMemoryUserDetailsManager();

        userDetailsManager.createUser(
                User.withUsername("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build());

        userDetailsManager.createUser(User.withUsername("gerente")
                .password(encoder.encode("gerente123"))
                .roles("GERENTE")
                .build());

        userDetailsManager.createUser(User.withUsername("secretaria")
                .password(encoder.encode("secretaria123"))
                .roles("SECRETARIA")
                .build());


        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                        "/cursos/**",
                                "/fotos/**",
                                "/categorias/**",
                                "/atividades/**"
                                ).permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}