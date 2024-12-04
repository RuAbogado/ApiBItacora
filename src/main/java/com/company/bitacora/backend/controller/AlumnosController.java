package com.company.bitacora.backend.controller;

import com.company.bitacora.backend.model.Alumnos;
import com.company.bitacora.backend.service.AlumnosService;
import com.company.bitacora.backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class AlumnosController {

    @Autowired
    private AlumnosService alumnosService;

    @GetMapping("/alumnos")
    public ResponseEntity<?> getAllAlumnos() {
        List<Alumnos> alumnos = alumnosService.getAlumnos();
        return ResponseEntity.ok(Map.of(
                "message", "Alumnos obtenidos con éxito",
                "data", alumnos,
                "codigo", 200
        ));
    }

    @GetMapping("/alumnos/{id}")
    public ResponseEntity<?> getAlumno(@PathVariable Long id) {
        Alumnos alumno = alumnosService.getAlumnoById(id);
        if (alumno != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Alumno obtenido con éxito",
                    "data", alumno,
                    "codigo", 200
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Alumno no encontrado",
                    "codigo", 404
            ));
        }
    }

    @Autowired
    private JwtService jwtService;
    @PostMapping("/alumnos")
    public ResponseEntity<?> createAlumno(@RequestBody Alumnos alumnos) {
        try {
            // 1. Cifrar la contraseña antes de guardarla
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encryptedPassword = passwordEncoder.encode(alumnos.getContrasena());

            // 2. Crear un nuevo objeto alumno con la contraseña cifrada
            alumnos.setContrasena(encryptedPassword);

            // 3. Guardar el alumno en la base de datos
            Alumnos newAlumnos = alumnosService.saveAlumno(alumnos);

            // 4. Crear un UserDetails para Spring Security, usando el correo y contraseña cifrada
            UserDetails newUser = User.builder()
                    .username(alumnos.getCorreo())  // Correo como nombre de usuario
                    .password(encryptedPassword)
                    .roles("Alumno")  // El rol de "Alumno"
                    .build();

            // 5. Generar el token JWT con los detalles del usuario
            final String jwt = jwtService.generateToken(newUser);

            // 6. Retornar el token en la respuesta
            return ResponseEntity
                    .status(201)  // Código HTTP 201 (Created)
                    .body(Map.of(
                            "message", "Alumno registrado con éxito",
                            "data", newAlumnos,
                            "codigo", 201,
                            "token", jwt  // Incluir el token en la respuesta
                    ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Error al registrar alumno"));
        }
    }



    @PutMapping("/alumnos/{id}")
    public ResponseEntity<?> updateAlumno(@PathVariable Long id, @RequestBody Alumnos alumnos) {
        Alumnos updatedAlumnos = alumnosService.updateAlumno(id, alumnos);
        if (updatedAlumnos != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Alumno actualizado con éxito",
                    "data", updatedAlumnos,
                    "codigo", 200
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Alumno no encontrado",
                    "codigo", 404
            ));
        }
    }

    @DeleteMapping("/alumnos/{id}")
    public ResponseEntity<?> deleteAlumno(@PathVariable Long id) {
        boolean deleted = alumnosService.deleteAlumno(id);
        if (deleted) {
            return ResponseEntity.status(204).body(Map.of(
                    "message", "Alumno eliminado con éxito",
                    "codigo", 204
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Alumno no encontrado",
                    "codigo", 404
            ));
        }
    }
}
