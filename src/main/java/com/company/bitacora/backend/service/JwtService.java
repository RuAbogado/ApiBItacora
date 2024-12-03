package com.company.bitacora.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    // Clave secreta utilizada para firmar el JWT, debe ser mantenida en secreto
    private static final String JWT_SECRET_KEY = "losmerosmerosoa4eee";

    // Validez del token en milisegundos (1 hora)
    public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * (long) 1; // 1 hora

    // Método para extraer el nombre de usuario (subject) desde el token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Extrae el subject (nombre de usuario) del token
    }

    // Método para extraer la fecha de expiración del token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Extrae la fecha de expiración del token
    }

    // Método genérico para extraer un "claim" específico del token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token)); // Aplica la función de resolución al token
    }

    // Método para extraer todos los claims (información) del token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()  // Parser para parsear el JWT
                .setSigningKey(JWT_SECRET_KEY)  // Usa la clave secreta para verificar la firma
                .parseClaimsJws(token)  // Parsea el JWT y obtiene el cuerpo (claims)
                .getBody();  // Retorna el cuerpo de los claims
    }

    // Método que verifica si el token ha expirado comparando la fecha de expiración con la fecha actual
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Si la fecha de expiración es antes de la fecha actual, el token ha expirado
    }

    // Método para generar un nuevo token JWT para un usuario
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();  // Crea un mapa de claims adicionales que se añadirán al token
        // Extrae el rol del usuario (el primer rol) y lo añade a los claims
        var rol = userDetails.getAuthorities().stream().collect(Collectors.toList()).get(0);
        claims.put("rol", rol);  // Se añade el rol al token
        return createToken(claims, userDetails.getUsername()); // Crea y retorna el token con los claims y el nombre de usuario
    }

    // Método para crear un token JWT con los claims y el nombre de usuario
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts
                .builder()  // Crea el constructor del token
                .setClaims(claims)  // Establece los claims
                .setSubject(subject)  // Establece el sujeto (nombre de usuario)
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Establece la fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))  // Establece la fecha de expiración (1 hora después)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)  // Firma el token con el algoritmo HS256 y la clave secreta
                .compact();  // Genera el token como un string compacto
    }

    // Método para validar un token JWT comparando el nombre de usuario del token con el del usuario actual
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);  // Extrae el nombre de usuario del token
        // Valida el token asegurándose que el nombre de usuario coincida y que el token no haya expirado
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
