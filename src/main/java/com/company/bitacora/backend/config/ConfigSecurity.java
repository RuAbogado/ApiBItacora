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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);  // Utiliza JdbcUserDetailsManager para leer usuarios de la base de datos.
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configure -> configure
                        .requestMatchers(HttpMethod.POST, "/v1/authenticate").permitAll()  // Ruta pública para autenticarse
                        .requestMatchers(HttpMethod.GET, "/v1/alumnos").permitAll()  // Ruta pública para obtener alumnos
                        .requestMatchers(HttpMethod.POST, "/v1/alumnos").permitAll()  // Ruta pública para crear un alumno
                        .requestMatchers(HttpMethod.PUT, "/v1/alumnos/**").permitAll()  // Ruta pública para editar un alumno
                        .requestMatchers(HttpMethod.GET, "/v1/alumnos/**").permitAll()  // Ruta pública para obtener alumnos por ID
                        .requestMatchers(HttpMethod.GET, "/v1/bitacoras").permitAll()  // Ruta pública para obtener bitácoras
                        .requestMatchers(HttpMethod.GET, "/v1/equipos").permitAll()  // Ruta pública para obtener equipos
                        .requestMatchers(HttpMethod.POST, "/v1/equipos").hasRole("Admin")  // Requiere rol Admin
                        .requestMatchers(HttpMethod.PUT, "/v1/equipos/**").hasRole("Admin")  // Requiere rol Admin
                        .requestMatchers(HttpMethod.DELETE, "/v1/equipos/**").hasRole("Admin")  // Requiere rol Admin
                        .requestMatchers(HttpMethod.GET, "/v1/salones").hasRole("Admin")  // Requiere rol Admin
                        .requestMatchers(HttpMethod.POST, "/v1/salones").hasRole("Admin")  // Requiere rol Admin
                        .requestMatchers(HttpMethod.PUT, "/v1/salones/**").hasRole("Admin")  // Requiere rol Admin
                        .requestMatchers(HttpMethod.DELETE, "/v1/salones/**").hasRole("Admin")  // Requiere rol Admin
                        .requestMatchers(HttpMethod.GET, "/v1/tecnicos").hasRole("Admin")  // Requiere rol Admin
                        .requestMatchers(HttpMethod.POST, "/v1/tecnicos").hasRole("Admin")  // Requiere rol Admin
                        .requestMatchers(HttpMethod.PUT, "/v1/tecnicos/**").hasRole("Admin")  // Requiere rol Admin
                        .requestMatchers(HttpMethod.DELETE, "/v1/tecnicos/**").hasRole("Admin")  // Requiere rol Admin
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
                .csrf(csrf -> csrf.disable())  // Deshabilitar CSRF
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Stateless porque usamos JWT
                .addFilterBefore(jwtReqFilter, UsernamePasswordAuthenticationFilter.class);  // Agregar filtro JWT

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Puedes elegir otro tipo de PasswordEncoder si prefieres
    }
}

