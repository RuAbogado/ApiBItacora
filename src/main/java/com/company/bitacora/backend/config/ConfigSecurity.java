package com.company.bitacora.backend.config;

import com.company.bitacora.backend.filter.JwtReqFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class ConfigSecurity {

    @Autowired

    @Lazy
    private JwtReqFilter jwtReqFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configure -> configure
                        .requestMatchers(HttpMethod.POST, "/v1/authenticate").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/alumnos").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/alumnos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/alumnos/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/v1/alumnos/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/v1/alumnos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/bitacoras").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/bitacoras").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/v1/bitacoras/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/v1/bitacoras/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/equipos").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/equipos").hasRole("Admin")
                        .requestMatchers(HttpMethod.PUT, "/v1/equipos/**").hasRole("Admin")
                        .requestMatchers(HttpMethod.DELETE, "/v1/equipos/**").hasRole("Admin")
                        .requestMatchers(HttpMethod.GET, "/v1/salones").hasRole("Admin")
                        .requestMatchers(HttpMethod.POST, "/v1/salones").hasRole("Admin")
                        .requestMatchers(HttpMethod.PUT, "/v1/salones/**").hasRole("Admin")
                        .requestMatchers(HttpMethod.DELETE, "/v1/salones/**").hasRole("Admin")
                        .requestMatchers(HttpMethod.GET, "/v1/tecnicos").hasRole("Admin")
                        .requestMatchers(HttpMethod.POST, "/v1/tecnicos").hasRole("Admin")
                        .requestMatchers(HttpMethod.PUT, "/v1/tecnicos/**").hasRole("Admin")
                        .requestMatchers(HttpMethod.DELETE, "/v1/tecnicos/**").hasRole("Admin")

                )
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new org.springframework.web.cors.CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:8080"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    config.setExposedHeaders(List.of("Authorization"));
                    return config;
                }))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtReqFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin1234")
                .roles("Admin")
                .build();

        UserDetails hugo = User.builder()
                .username("hugo")
                .password("{noop}hugo123")
                .roles("Empleado")
                .build();

        UserDetails edita = User.builder()
                .username("edita")
                .password("{noop}edita123")
                .roles("Alumno")
                .build();

        return new InMemoryUserDetailsManager(admin, hugo, edita);
    }
}
