package com.company.bitacora.backend.controller;


import com.company.bitacora.backend.model.Equipo;
import com.company.bitacora.backend.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("equipo")
public class EquipoController {
    @Autowired
    private EquipoService equipoService;

    @GetMapping
    public ResponseEntity<List<Equipo>> listar() {
        List<Equipo> equipos = equipoService.getEquipos();
        return ResponseEntity.ok(equipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> buscar(@PathVariable Long id) {
        Equipo equipo = equipoService.getEquipoById(id);
        return ResponseEntity.ok(equipo);
    }

    @PostMapping
    public ResponseEntity<Equipo> crear(@RequestBody Equipo equipo) {
        Equipo saveEquipo = equipoService.saveEquipo( equipo);
        return ResponseEntity.ok(saveEquipo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> update(@PathVariable Long id, @RequestBody Equipo equipo) {
        Equipo updateEquipo = equipoService.updateEquipo( id, equipo);
        return ResponseEntity.ok(updateEquipo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Equipo> eliminar(@PathVariable Long id) {
        equipoService.deleteEquipo( id);
        return ResponseEntity.noContent().build();
    }
}
