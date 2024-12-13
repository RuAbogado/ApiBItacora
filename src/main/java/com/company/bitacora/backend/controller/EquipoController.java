package com.company.bitacora.backend.controller;

import com.company.bitacora.backend.model.Equipo;
import com.company.bitacora.backend.model.Salon;
import com.company.bitacora.backend.service.EquipoService;
import com.company.bitacora.backend.service.SalonService;
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

    @Autowired
    private SalonService salonService; // Necesitamos el servicio de Salon para obtener el salón relacionado.

    // Listar todos los equipos
    @GetMapping("/equipos")
    public ResponseEntity<?> listar() {
        List<Equipo> equipos = equipoService.getEquipos();
        return ResponseEntity.ok(Map.of(
                "message", "Equipos obtenidos con éxito",
                "data", equipos,
                "codigo", 200
        ));
    }

    // Buscar un equipo por ID
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

    // Crear un nuevo equipo asociado a un salón
    @PostMapping("/equipos")
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> requestData) {
        // Extraer datos del cuerpo de la solicitud
        String marca = (String) requestData.get("marca");
        String modelo = (String) requestData.get("modelo");
        String numeroSerie = (String) requestData.get("numeroSerie");
        Long salonId = ((Number) requestData.get("salonId")).longValue(); // Obtener el id del salón

        // Buscar el salón relacionado
        Salon salon = salonService.getSalonById(salonId);
        if (salon == null) {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Salón no encontrado",
                    "codigo", 404
            ));
        }

        // Crear y guardar el equipo
        Equipo equipo = new Equipo(marca, modelo, numeroSerie, salon);
        Equipo savedEquipo = equipoService.saveEquipo(equipo);

        return ResponseEntity.status(201).body(Map.of(
                "message", "Equipo registrado con éxito",
                "data", savedEquipo,
                "codigo", 201
        ));
    }

    // Actualizar un equipo existente
    @PutMapping("/equipos/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Map<String, Object> requestData) {
        // Extraer datos del cuerpo de la solicitud
        String marca = (String) requestData.get("marca");
        String modelo = (String) requestData.get("modelo");
        String numeroSerie = (String) requestData.get("numeroSerie");
        Long salonId = ((Number) requestData.get("salonId")).longValue(); // Obtener el id del salón

        // Buscar el salón relacionado
        Salon salon = salonService.getSalonById(salonId);
        if (salon == null) {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Salón no encontrado",
                    "codigo", 404
            ));
        }

        // Actualizar el equipo
        Equipo updatedEquipo = equipoService.updateEquipo(id, new Equipo(marca, modelo, numeroSerie, salon));
        if (updatedEquipo != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Equipo actualizado con éxito",
                    "data", updatedEquipo,
                    "codigo", 200
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Equipo no encontrado",
                    "codigo", 404
            ));
        }
    }

    // Eliminar un equipo
    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean deleted = equipoService.deleteEquipo(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Equipo no encontrado",
                    "codigo", 404
            ));
        }
    }
}
