package com.example.authjwt.security;

import com.example.authjwt.config.ApplicationRequestFilter;
import com.example.authjwt.service.auth.ApplicationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class ApplicationSecurity {

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Autowired
    private ApplicationRequestFilter requestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .cors().configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    // configuration.setAllowedOrigins(List.of("http://localhost:9000"));
                    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
                    configuration.setAllowedHeaders(List.of("*"));
                    return configuration;
                }).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(  "/auth/*").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

}
