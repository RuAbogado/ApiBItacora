package com.company.bitacora.backend.controller;


import com.company.bitacora.backend.model.Salon;
import com.company.bitacora.backend.service.SalonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class SalonController {
    @Autowired
    private SalonService salonService;

    @GetMapping("/salones")
    public ResponseEntity<List<Salon>> getSalones() {
        List<Salon> salones = salonService.getSalones();
        return ResponseEntity.ok(salones);
    }

    @GetMapping("/salones/{id}")
    public ResponseEntity<Salon> getSalone(@PathVariable Long id) {
        Salon salon = salonService.getSalonById(id);
        return ResponseEntity.ok(salon);
    }

    @PostMapping("/salones")
    public ResponseEntity<Salon> createSalon(@RequestBody Salon salon) {
        Salon newSalon = salonService.saveSalon(salon);
        return ResponseEntity.ok(newSalon);
    }

    @PutMapping("/salones/{id}")
    public ResponseEntity<Salon> updateSalon(@PathVariable Long id, @RequestBody Salon salon) {
        Salon updatedSalon = salonService.updateSalon(id, salon);
        return ResponseEntity.ok(updatedSalon);
    }

    @DeleteMapping("/salones/{id}")
    public ResponseEntity<Salon> deleteSalon(@PathVariable Long id) {
        salonService.deleteSalon(id);
        return ResponseEntity.noContent().build();
    }
}
