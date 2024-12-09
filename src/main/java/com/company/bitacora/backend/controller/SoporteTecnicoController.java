package com.company.bitacora.backend.controller;

import com.company.bitacora.backend.model.SoporteTecnico;
import com.company.bitacora.backend.service.SoporteTecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1")
public class SoporteTecnicoController {

    @Autowired
    private SoporteTecnicoService soporteTecnicoService;

    @GetMapping("/tecnicos")
    public ResponseEntity<?> getAll() {
        List<SoporteTecnico> tecnicos = soporteTecnicoService.listarTecnicos();
        return ResponseEntity.ok(Map.of(
                "message", "Técnicos obtenidos con éxito",
                "data", tecnicos,
                "codigo", 200
        ));
    }

    @GetMapping("/tecnicos/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        SoporteTecnico tecnico = soporteTecnicoService.getSoporteTecnico(id);
        if (tecnico != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Técnico encontrado con éxito",
                    "data", tecnico,
                    "codigo", 200
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Técnico no encontrado",
                    "codigo", 404
            ));
        }
    }

    @PostMapping("/tecnicos")
    public ResponseEntity<?> create(@RequestBody SoporteTecnico soporteTecnico) {
        SoporteTecnico newTecnico = soporteTecnicoService.saveSoporteTecnico(soporteTecnico);
        return ResponseEntity.status(201).body(Map.of(
                "message", "Técnico creado con éxito",
                "data", newTecnico,
                "codigo", 201
        ));
    }

    @PutMapping("/tecnicos/{correo}")
    public ResponseEntity<?> updateTecnico(@PathVariable String correo, @RequestBody SoporteTecnico soporteTecnico) {
        SoporteTecnico updatedTecnico = soporteTecnicoService.updateSoporteTecnico(correo, soporteTecnico);
        if (updatedTecnico != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Técnico actualizado con éxito",
                    "data", updatedTecnico,
                    "codigo", 200
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Técnico no encontrado",
                    "codigo", 404
            ));
        }
    }

    @DeleteMapping("/tecnicos/{correo}")
    public ResponseEntity<?> deleteTecnico(@PathVariable String correo) {
        boolean deleted = soporteTecnicoService.deleteSoporteTecnico(correo);
        if (deleted) {
            return ResponseEntity.status(204).body(Map.of(
                    "message", "Técnico eliminado con éxito",
                    "codigo", 204
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Técnico no encontrado",
                    "codigo", 404
            ));
        }
    }
}
