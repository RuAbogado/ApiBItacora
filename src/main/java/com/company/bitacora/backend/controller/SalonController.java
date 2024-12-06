package com.company.bitacora.backend.controller;

import com.company.bitacora.backend.model.Salon;
import com.company.bitacora.backend.service.SalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1")
public class SalonController {

    @Autowired
    private SalonService salonService;

    @GetMapping("/salones")
    public ResponseEntity<?> getSalones() {
        List<Salon> salones = salonService.getSalones();
        return ResponseEntity.ok(Map.of(
                "message", "Salones obtenidos con éxito",
                "data", salones,
                "codigo", 200
        ));
    }

    @GetMapping("/salones/{id}")
    public ResponseEntity<?> getSalon(@PathVariable Long id) {
        Salon salon = salonService.getSalonById(id);
        if (salon != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Salón obtenido con éxito",
                    "data", salon,
                    "codigo", 200
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Salón no encontrado",
                    "codigo", 404
            ));
        }
    }

    @PostMapping("/salones")
    public ResponseEntity<?> createSalon(@RequestBody Salon salon) {
        Salon newSalon = salonService.saveSalon(salon);
        return ResponseEntity.status(201).body(Map.of(
                "message", "Salón registrado con éxito",
                "data", newSalon,
                "codigo", 201
        ));
    }

    @PutMapping("/salones/{id}")
    public ResponseEntity<?> updateSalon(@PathVariable Long id, @RequestBody Salon salon) {
        Salon updatedSalon = salonService.updateSalon(id, salon);
        if (updatedSalon != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Salón actualizado con éxito",
                    "data", updatedSalon,
                    "codigo", 200
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Salón no encontrado",
                    "codigo", 404
            ));
        }
    }

    @DeleteMapping("/salones/{id}")
    public ResponseEntity<?> deleteSalon(@PathVariable Long id) {
        boolean deleted = salonService.deleteSalon(id);
        if (deleted) {
            return ResponseEntity.status(204).body(Map.of(
                    "message", "Salón eliminado con éxito",
                    "codigo", 204
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Salón no encontrado",
                    "codigo", 404
            ));
        }
    }
}
