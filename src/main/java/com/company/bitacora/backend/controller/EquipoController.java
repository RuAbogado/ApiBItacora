package com.company.bitacora.backend.controller;

import com.company.bitacora.backend.model.Equipo;
import com.company.bitacora.backend.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @GetMapping("/equipos")
    public ResponseEntity<?> listar() {
        List<Equipo> equipos = equipoService.getEquipos();
        return ResponseEntity.ok(Map.of(
                "message", "Equipos obtenidos con éxito",
                "data", equipos,
                "codigo", 200
        ));
    }

    @GetMapping("/equipos/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Equipo equipo = equipoService.getEquipoById(id);
        if (equipo != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Equipo obtenido con éxito",
                    "data", equipo,
                    "codigo", 200
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Equipo no encontrado",
                    "codigo", 404
            ));
        }
    }

    @PostMapping("/equipos")
    public ResponseEntity<?> crear(@RequestBody Equipo equipo) {
        Equipo saveEquipo = equipoService.saveEquipo(equipo);
        return ResponseEntity.status(201).body(Map.of(
                "message", "Equipo registrado con éxito",
                "data", saveEquipo,
                "codigo", 201
        ));
    }

    @PutMapping("/equipos/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Equipo equipo) {
        Equipo updateEquipo = equipoService.updateEquipo(id, equipo);
        if (updateEquipo != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Equipo actualizado con éxito",
                    "data", updateEquipo,
                    "codigo", 200
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Equipo no encontrado",
                    "codigo", 404
            ));
        }
    }

    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean deleted = equipoService.deleteEquipo(id);
        if (deleted) {
            return ResponseEntity.status(204).body(Map.of(
                    "message", "Equipo eliminado con éxito",
                    "codigo", 204
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Equipo no encontrado",
                    "codigo", 404
            ));
        }
    }
}
