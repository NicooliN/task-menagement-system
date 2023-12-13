package ru.petapp.taskmenagementsystem.taskmenagment.config;//package com.sber.java13spring.java13springproject.libraryproject.config.jwt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.petapp.taskmenagementsystem.taskmenagment.service.userdetails.CustomUserDetailsService;

import java.util.Arrays;
import java.util.Collections;

import static ru.petapp.taskmenagementsystem.taskmenagment.constants.SecurityConstants.*;
import static ru.petapp.taskmenagementsystem.taskmenagment.constants.UserRolesConstants.*;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JWTSecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JWTTokenFilter jwtTokenFilter;

    public JWTSecurityConfig(CustomUserDetailsService customUserDetailsService,
                             JWTTokenFilter jwtTokenFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenFilter = jwtTokenFilter;
//        @Autowired
//    @Qualifier("restAuthenticationEntryPoint")
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//
    }

    @Autowired
    @Qualifier("restAuthenticationEntryPoint")
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

//    @Autowired
//    private CorsFilter corsFilter;

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//              .allowedOrigins("*")
//              .allowedMethods("PUT", "DELETE", "POST", "GET")
//              .allowedHeaders("*")
//              .exposedHeaders("*")
//              .allowCredentials(false).maxAge(3600);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
//              .addFilterBefore(corsFilter, SessionManagementFilter.class)
                .cors().disable()
                .csrf().disable()
              //Настройка http запросов - кому куда можно/нельзя
              .authorizeHttpRequests(auth -> auth
                                           .requestMatchers(RESOURCES_WHITE_LIST.toArray(String[]::new)).permitAll()
                                           .requestMatchers(USERS_WHITE_LIST.toArray(String[]::new)).permitAll()
                                           .requestMatchers(USERS_WHITE_LIST.toArray(String[]::new)).permitAll()
                                           .requestMatchers(TASKS_WHITE_LIST.toArray(String[]::new)).permitAll()
                                           .requestMatchers(TASKS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, COMMANDER)
                                         .requestMatchers(USERS_PERMISSION_LIST.toArray(String[]::new)).hasRole(USER)
                                           .anyRequest().authenticated()
                                    )
              .exceptionHandling()
              .authenticationEntryPoint(restAuthenticationEntryPoint)
              .and()
              .sessionManagement(
                    session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              //JWT Token Filter VALID or NOT
              .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
              .userDetailsService(customUserDetailsService)
              .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
          AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
