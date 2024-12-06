package com.company.bitacora.backend.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncriptarContrasenia {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "admin1234"; // Contraseña en texto claro
        String encodedPassword = passwordEncoder.encode(rawPassword); // Contraseña encriptada
        System.out.println(encodedPassword); // Muestra la contraseña encriptada
    }
}
