package com.company.bitacora.backend.controller;

import com.company.bitacora.backend.model.Alumnos;
import com.company.bitacora.backend.service.AlumnosService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/alumnos")
    public ResponseEntity<?> createAlumno(@RequestBody Alumnos alumnos) {
        Alumnos newAlumnos = alumnosService.saveAlumno(alumnos);
        return ResponseEntity
                .status(201) // Código HTTP 201 (Created)
                .body(Map.of(
                        "message", "Alumno registrado con éxito",
                        "data", newAlumnos,
                        "codigo", 201
                ));
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
