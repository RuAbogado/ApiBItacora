package com.company.bitacora.backend.config;

import com.company.bitacora.backend.filter.JwtReqFilter;
import com.company.bitacora.backend.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration // Indica que esta clase es una configuración de Spring.
public class ConfigSecurity {

    @Autowired
    @Lazy
    private JwtReqFilter jwtReqFilter; // Se inyecta un filtro que gestionará la validación de JWT.

    // Bean que define el servicio de detalles de usuario, en este caso se utiliza un JdbcUserDetailsManager
    // para obtener los usuarios de una base de datos.
    @Bean
    public UserDetailsService userDetailsService(UserDao userDao) {
        return username -> {
            Optional<com.company.bitacora.backend.model.User> userOptional = userDao.findByUsername(username);
            com.company.bitacora.backend.model.User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            Set<GrantedAuthority> authorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                    .collect(Collectors.toSet());

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        };
    }



    // Bean que define la configuración de seguridad HTTP, donde se configuran las reglas de acceso
    // y autenticación para las rutas de la API.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configure -> configure
                                // Se configuran las reglas de autorización para las rutas según el método HTT
                                .requestMatchers(HttpMethod.POST, "/v1/authenticate").permitAll()
                                .requestMatchers(HttpMethod.GET, "/v1/alumnos").permitAll()
                                .requestMatchers(HttpMethod.POST, "/v1/alumnos").permitAll() // Solo Admin y Alumno pueden acceder
                                .requestMatchers(HttpMethod.PUT, "/v1/alumnos/**").permitAll()// Solo Admin y Alumno pueden acceder
                                .requestMatchers(HttpMethod.GET, "/v1/alumnos/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/v1/alumnos/**").permitAll()// Solo Admin puede eliminar
                                .requestMatchers(HttpMethod.GET, "/v1/bitacoras").permitAll()
                                .requestMatchers(HttpMethod.GET, "/v1/bitacoras/**").permitAll()// Admin y Empleado pueden acceder
                                .requestMatchers(HttpMethod.POST, "/v1/bitacoras").permitAll()// Solo Alumno puede crear
                                .requestMatchers(HttpMethod.PUT, "/v1/bitacoras/**").permitAll()// Solo Empleado puede modificar
                                .requestMatchers(HttpMethod.DELETE, "/v1/bitacoras/**").permitAll() // Empleado y Admin pueden eliminar
                                .requestMatchers(HttpMethod.GET, "/v1/equipos").hasRole("Admin")
                                .requestMatchers(HttpMethod.GET, "/v1/equipos/**").hasRole("Admin")// Admin y Empleado pueden acceder
                                .requestMatchers(HttpMethod.POST, "/v1/equipos").hasRole("Admin") // Solo Admin puede crear
                                .requestMatchers(HttpMethod.PUT, "/v1/equipos/**").hasRole("Admin") // Solo Admin puede modificar
                                .requestMatchers(HttpMethod.DELETE, "/v1/equipos/**").hasRole("Admin") // Solo Admin puede eliminar
                                .requestMatchers(HttpMethod.GET, "/v1/salones").hasRole("Admin")
                                .requestMatchers(HttpMethod.GET, "/v1/salones/**").hasRole("Admin") // Admin y Empleado pueden acceder
                                .requestMatchers(HttpMethod.POST, "/v1/salones").hasRole("Admin") // Solo Admin puede crear
                                .requestMatchers(HttpMethod.PUT, "/v1/salones/**").hasRole("Admin") // Solo Admin puede modificar
                                .requestMatchers(HttpMethod.DELETE, "/v1/salones/**").hasRole("Admin") // Solo Admin puede eliminar
                                .requestMatchers(HttpMethod.GET, "/v1/tecnicos").hasRole("Admin")
                                .requestMatchers(HttpMethod.GET, "/v1/tecnicos/**").hasRole("Admin")// Solo Admin puede acceder
                                .requestMatchers(HttpMethod.POST, "/v1/tecnicos").hasRole("Admin") // Solo Admin puede crear
                                .requestMatchers(HttpMethod.PUT, "/v1/tecnicos/**").hasRole("Admin") // Solo Admin puede modificar
                                .requestMatchers(HttpMethod.DELETE, "/v1/tecnicos/**").hasRole("Admin") // Solo Admin puede eliminar
                        // Ruta pública para la autenticación
                )
                // Configuración de CORS para permitir solicitudes desde un origen específico
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new org.springframework.web.cors.CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://192.168.105.148:8080")); // Ajusta el origen según el cliente
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Métodos permitidos
                    config.setAllowCredentials(true); // Permite credenciales en las solicitudes CORS
                    config.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Especificar encabezados permitidos
                    config.setExposedHeaders(List.of("Authorization")); // Encabezados visibles para el cliente                    return config;
                    return config;
                }))
                // Deshabilitar CSRF (Cross-Site Request Forgery) porque la aplicación probablemente está utilizando JWT
                .csrf(csrf -> csrf.disable())
                // Configuración para que la aplicación no mantenga sesiones (stateless), ya que se usa JWT para autenticación
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Añadir el filtro JWT antes del filtro de autenticación por nombre de usuario y contraseña
                .addFilterBefore(jwtReqFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Construir la configuración de seguridad
    }

    // Bean que proporciona un AuthenticationManager para la autenticación de usuarios
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usando BCrypt para codificar las contraseñas
    }

}
