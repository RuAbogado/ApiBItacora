package com.company.bitacora.backend.controller;

import com.company.bitacora.backend.request.AuthRequest;
import com.company.bitacora.backend.response.TokenResponse;
import com.company.bitacora.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class TokenController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody AuthRequest request) {
        try {
            // Autenticar al usuario
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getContrasenia()));

            // Cargar los detalles del usuario después de la autenticación
            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsuario());

            // Generar el token JWT
            final String jwt = jwtService.generateToken(userDetails);

            // Devolver la respuesta con el token
            return ResponseEntity.ok(new TokenResponse(jwt));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new TokenResponse("Autenticación fallida"));
        }
    }
}

