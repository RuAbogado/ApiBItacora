package com.company.bitacora.backend.controller;

import com.company.bitacora.backend.model.Bitacora;
import com.company.bitacora.backend.response.BitacoraResponseRest;
import com.company.bitacora.backend.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class BitacoraController {

    @Autowired
    private BitacoraService bitacoraService;

    @GetMapping("/bitacoras")
    public ResponseEntity<?> getBitacoras() {
        List<Bitacora> bitacoras = bitacoraService.buscarBitacoras();
        return ResponseEntity.ok(Map.of(
                "message", "Bitácoras encontradas con éxito",
                "data", bitacoras,
                "codigo", 200
        ));
    }

    @GetMapping("/bitacoras/{id}")
    public ResponseEntity<?> getBitacoraById(@PathVariable Long id) {
        Bitacora bitacora = bitacoraService.buscarPorID(id);
        if (bitacora != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Bitácora encontrada con éxito",
                    "data", bitacora,
                    "codigo", 200
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Bitácora no encontrada",
                    "codigo", 404
            ));
        }
    }

    @PostMapping("/bitacoras")
    public ResponseEntity<?> crear(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id_salon") Long idSalon,
            @RequestParam("id_equipo") Long idEquipo,
            @RequestParam("correo") String correo,
            @RequestParam("fecha") String fecha,
            @RequestParam("horaEntrada") String horaEntrada,
            @RequestParam("horaSalida") String horaSalida,
            @RequestParam("maestro") String maestro,
            @RequestParam("grado") String grado,
            @RequestParam("grupo") String grupo,
            @RequestParam("observaciones") String observaciones
    ) {
        // Crea la bitácora con los datos recibidos
        Bitacora newBitacora = bitacoraService.crearBitacora(idSalon, idEquipo, correo, fecha, horaEntrada, horaSalida, maestro, grado, grupo, observaciones, file);
        return ResponseEntity.status(201).body(Map.of(
                "message", "Bitácora registrada con éxito",
                "data", newBitacora,
                "codigo", 201
        ));
    }



    @PutMapping("/bitacoras/{id}")
    public ResponseEntity<?> actualizar(@RequestBody Bitacora bitacora, @PathVariable Long id) {
        Bitacora updatedBitacora = bitacoraService.actualizarBitacora(bitacora, id);
        if (updatedBitacora != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Bitácora actualizada con éxito",
                    "data", updatedBitacora,
                    "codigo", 200
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Bitácora no encontrada",
                    "codigo", 404
            ));
        }
    }

    @PatchMapping("/bitacoras/{id}")
    public ResponseEntity<?> upImg(@RequestParam("file") MultipartFile img, @PathVariable Long id) {
        Bitacora updatedBitacora = bitacoraService.upImagen(img, id);
        if (updatedBitacora != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Imagen subida con éxito",
                    "data", updatedBitacora,
                    "codigo", 200
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Bitácora no encontrada",
                    "codigo", 404
            ));
        }
    }

    @DeleteMapping("/bitacoras/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean deleted = bitacoraService.eliminarBitacora(id);
        if (deleted) {
            return ResponseEntity.status(204).body(Map.of(
                    "message", "Bitácora eliminada con éxito",
                    "codigo", 204
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Bitácora no encontrada",
                    "codigo", 404
            ));
        }
    }
}
