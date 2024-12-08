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

    // Obtener todos los técnicos
    @GetMapping("/tecnicos")
    public ResponseEntity<?> getAll() {
        try {
            List<SoporteTecnico> tecnicos = soporteTecnicoService.listarTecnicos();
            if (tecnicos.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of(
                        "message", "No se encontraron técnicos",
                        "codigo", 404
                ));
            }
            return ResponseEntity.ok(Map.of(
                    "message", "Técnicos obtenidos con éxito",
                    "data", tecnicos,
                    "codigo", 200
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "message", "Error al obtener los técnicos: " + e.getMessage(),
                    "codigo", 500
            ));
        }
    }

    // Obtener técnico por ID
    @GetMapping("/tecnicos/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            SoporteTecnico tecnico = soporteTecnicoService.getSoporteTecnico(id);
            if (tecnico != null) {
                return ResponseEntity.ok(Map.of(
                        "message", "Técnico encontrado con éxito",
                        "data", tecnico,
                        "codigo", 200
                ));
            } else {
                return ResponseEntity.status(404).body(Map.of(
                        "message", "Técnico con ID " + id + " no encontrado",
                        "codigo", 404
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "message", "Error al obtener el técnico: " + e.getMessage(),
                    "codigo", 500
            ));
        }
    }

    // Crear un nuevo técnico
    @PostMapping("/tecnicos")
    public ResponseEntity<?> create(@RequestBody SoporteTecnico soporteTecnico) {
        try {
            // Validar campos de entrada
            if (soporteTecnico.getNombre() == null || soporteTecnico.getCorreo() == null || soporteTecnico.getTipo() == null) {
                return ResponseEntity.status(400).body(Map.of(
                        "message", "Los campos nombre, correo y tipo son obligatorios.",
                        "codigo", 400
                ));
            }

            SoporteTecnico newTecnico = soporteTecnicoService.saveSoporteTecnico(soporteTecnico);
            return ResponseEntity.status(201).body(Map.of(
                    "message", "Técnico creado con éxito",
                    "data", newTecnico,
                    "codigo", 201
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "message", "Error al crear el técnico: " + e.getMessage(),
                    "codigo", 500
            ));
        }
    }

    // Actualizar un técnico
    @PutMapping("/tecnicos/{id}")
    public ResponseEntity<?> updateTecnico(@PathVariable Long id, @RequestBody SoporteTecnico soporteTecnico) {
        try {
            // Validar campos de entrada
            if (soporteTecnico.getNombre() == null || soporteTecnico.getCorreo() == null || soporteTecnico.getTipo() == null) {
                return ResponseEntity.status(400).body(Map.of(
                        "message", "Los campos nombre, correo y tipo son obligatorios.",
                        "codigo", 400
                ));
            }

            // Actualizar el técnico
            SoporteTecnico updatedTecnico = soporteTecnicoService.updateSoporteTecnico(id, soporteTecnico);
            if (updatedTecnico != null) {
                return ResponseEntity.ok(Map.of(
                        "message", "Técnico actualizado con éxito",
                        "data", updatedTecnico,
                        "codigo", 200
                ));
            } else {
                return ResponseEntity.status(404).body(Map.of(
                        "message", "Técnico con ID " + id + " no encontrado",
                        "codigo", 404
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "message", "Error al actualizar el técnico: " + e.getMessage(),
                    "codigo", 500
            ));
        }
    }

    // Eliminar un técnico
    @DeleteMapping("/tecnicos/{id}")
    public ResponseEntity<?> deleteTecnico(@PathVariable Long id) {
        try {
            boolean deleted = soporteTecnicoService.deleteSoporteTecnico(id);
            if (deleted) {
                return ResponseEntity.status(204).body(Map.of(
                        "message", "Técnico eliminado con éxito",
                        "codigo", 204
                ));
            } else {
                return ResponseEntity.status(404).body(Map.of(
                        "message", "Técnico con ID " + id + " no encontrado",
                        "codigo", 404
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "message", "Error al eliminar el técnico: " + e.getMessage(),
                    "codigo", 500
            ));
        }
    }

    // Manejo de excepciones globales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(500).body(Map.of(
                "message", "Error interno del servidor: " + e.getMessage(),
                "codigo", 500
        ));
    }
}
