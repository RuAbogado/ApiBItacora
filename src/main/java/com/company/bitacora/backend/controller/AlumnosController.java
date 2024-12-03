package com.company.bitacora.backend.controller;


import com.company.bitacora.backend.model.Alumnos;
import com.company.bitacora.backend.service.AlumnosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class AlumnosController {
    @Autowired
    private AlumnosService alumnosService;

    @GetMapping("/alumnos")
    public ResponseEntity<List<Alumnos>> getAllAlumnos() {
        List<Alumnos> alumnos = alumnosService.getAlumnos();
        return ResponseEntity.ok(alumnos);
    }

    @GetMapping("/alumnos/{id}")
    public ResponseEntity<Alumnos> getAlumno(@PathVariable Long id) {
       Alumnos alumno = alumnosService.getAlumnoById(id);
        return ResponseEntity.ok(alumno);
    }

    @PostMapping("/alumnos")
    public ResponseEntity<Alumnos> createAlumno(@RequestBody Alumnos alumnos) {
        Alumnos newAlumnos = alumnosService.saveAlumno(alumnos);
        return ResponseEntity.ok(newAlumnos);
    }

    @PutMapping("/alumnos/{id}")
    public ResponseEntity<Alumnos> updateAlumno(@PathVariable Long id, @RequestBody Alumnos alumnos) {
        Alumnos updatedAlumnos = alumnosService.updateAlumno(id, alumnos);
        return ResponseEntity.ok(updatedAlumnos);
    }

    @DeleteMapping("/alumnos/{id}")
    public ResponseEntity<Alumnos> deleteAlumno(@PathVariable Long id) {
        alumnosService.deleteAlumno(id);
        return ResponseEntity.noContent().build();
    }
}
