package com.company.bitacora.backend.controller;

import com.company.bitacora.backend.exceptio.AlumnoNotFoundException;
import com.company.bitacora.backend.model.Alumnos;
import com.company.bitacora.backend.service.AlumnosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/alumnos/{correo}")
    public ResponseEntity<?> getAlumno(@PathVariable String correo) {
        Alumnos alumno = alumnosService.getAlumnoByCorreo(correo);
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

    @PostMapping("/alumnos")
    public ResponseEntity<?> createAlumno(@RequestBody Alumnos alumnos) {
        Alumnos newAlumnos = alumnosService.saveAlumno(alumnos);
        return ResponseEntity.status(201).body(Map.of(
                "message", "Alumno registrado con éxito",
                "data", newAlumnos,
                "codigo", 201
        ));
    }

    @PutMapping("/alumnos/{correo}")
    public ResponseEntity<?> updateAlumno(@PathVariable String correo, @RequestBody Alumnos alumnos) {
        try {
            Alumnos updatedAlumnos = alumnosService.updateAlumno(correo, alumnos);
            return ResponseEntity.ok(Map.of(
                    "message", "Alumno actualizado con éxito",
                    "data", updatedAlumnos,
                    "codigo", 200
            ));
        } catch (AlumnoNotFoundException ex) {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Alumno no encontrado",
                    "codigo", 404
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error al actualizar el alumno",
                    "codigo", 500
            ));
        }
    }

    @DeleteMapping("/alumnos/{correo}")
    public ResponseEntity<?> deleteAlumno(@PathVariable String correo) {
        try {
            boolean deleted = alumnosService.deleteAlumno(correo);
            return ResponseEntity.noContent().build();
        } catch (AlumnoNotFoundException ex) {
            return ResponseEntity.status(404).body(Map.of(
                    "message", ex.getMessage(),
                    "codigo", 404
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "message", "Error eliminando el alumno",
                    "codigo", 500
            ));
        }
    }
}
