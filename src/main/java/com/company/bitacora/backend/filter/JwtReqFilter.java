package com.company.bitacora.backend.filter;

import com.company.bitacora.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component  // Marca la clase como un componente de Spring para que sea gestionada por el contenedor de Spring.
public class JwtReqFilter extends OncePerRequestFilter {  // Extiende OncePerRequestFilter para garantizar que el filtro se ejecute una vez por solicitud

    @Autowired  // Inyecta el servicio de detalles de usuario de Spring Security (UserDetailsService)
    private UserDetailsService userDetailsService;

    @Autowired  // Inyecta el servicio JWT que maneja la validación y extracción del token
    private JwtService jwtService;

    // Método principal que intercepta las solicitudes y valida los tokens JWT
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Obtiene el encabezado 'Authorization' de la solicitud HTTP
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;  // Variable para almacenar el nombre de usuario extraído del token
        String jwt = null;  // Variable para almacenar el token JWT

        // Verifica si el encabezado 'Authorization' contiene un token JWT (comienza con "Bearer ")
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);  // Extrae el token JWT (eliminando "Bearer " al inicio)
            username = jwtService.extractUsername(jwt);  // Extrae el nombre de usuario del token JWT
        }

        // Si el nombre de usuario es válido y no hay autenticación en el contexto de seguridad actual
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Carga los detalles del usuario (roles, permisos, etc.) desde la base de datos utilizando el UserDetailsService
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Valida el token JWT con el servicio de JWT
            if (jwtService.validateToken(jwt, userDetails)) {

                // Si el token es válido, crea un objeto de autenticación con los detalles del usuario
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());  // Autoridades se extraen de UserDetails
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));  // Establece detalles adicionales (por ejemplo, IP)

                // Establece el objeto de autenticación en el contexto de seguridad de Spring
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Pasa la solicitud y la respuesta al siguiente filtro en la cadena de filtros
        chain.doFilter(request, response);
    }
}
