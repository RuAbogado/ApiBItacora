package com.company.bitacora.backend.controller;

import com.company.bitacora.backend.model.SoporteTecnico_Bitacora;
import com.company.bitacora.backend.service.SoporteTecnico_BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1")
public class SoporteTecnico_BitacoraController {

    @Autowired
    private SoporteTecnico_BitacoraService soporteTecnicoBitacoraService;

    @GetMapping("/bitacoras_tecnicos")
    public ResponseEntity<?> getAll() {
        List<SoporteTecnico_Bitacora> tecnicos = soporteTecnicoBitacoraService.BitacorasTecnicos();
        return ResponseEntity.ok(Map.of(
                "message", "Bitácoras técnicas obtenidas con éxito",
                "data", tecnicos,
                "codigo", 200
        ));
    }

    @GetMapping("/bitacoras_tecnicos/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        SoporteTecnico_Bitacora tecnicoBitacora = soporteTecnicoBitacoraService.BuscarBitacora(id);
        if (tecnicoBitacora != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Bitácora técnica encontrada con éxito",
                    "data", tecnicoBitacora,
                    "codigo", 200
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Bitácora técnica no encontrada",
                    "codigo", 404
            ));
        }
    }

    @PostMapping("/bitacoras_tecnicos")
    public ResponseEntity<?> createBitacoraTecnico(@RequestBody SoporteTecnico_Bitacora soporteTecnicoBitacora) {
        SoporteTecnico_Bitacora newBitacoraTecnico = soporteTecnicoBitacoraService.NuevaBitacora(soporteTecnicoBitacora);
        return ResponseEntity.status(201).body(Map.of(
                "message", "Bitácora técnica registrada con éxito",
                "data", newBitacoraTecnico,
                "codigo", 201
        ));
    }

    @PutMapping("/bitacoras_tecnicos/{id}")
    public ResponseEntity<?> updateBitacoraTecnico(@PathVariable Long id, @RequestBody SoporteTecnico_Bitacora soporteTecnicoBitacora) {
        SoporteTecnico_Bitacora updatedBitacoraTecnico = soporteTecnicoBitacoraService.actualizarBitacora(id, soporteTecnicoBitacora);
        if (updatedBitacoraTecnico != null) {
            return ResponseEntity.ok(Map.of(
                    "message", "Bitácora técnica actualizada con éxito",
                    "data", updatedBitacoraTecnico,
                    "codigo", 200
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Bitácora técnica no encontrada",
                    "codigo", 404
            ));
        }
    }

    @DeleteMapping("/bitacoras_tecnicos/{id}")
    public ResponseEntity<?> deleteBitacoraTecnico(@PathVariable Long id) {
        boolean deleted = soporteTecnicoBitacoraService.eliminarBitacora(id);
        if (deleted) {
            return ResponseEntity.status(204).body(Map.of(
                    "message", "Bitácora técnica eliminada con éxito",
                    "codigo", 204
            ));
        } else {
            return ResponseEntity.status(404).body(Map.of(
                    "message", "Bitácora técnica no encontrada",
                    "codigo", 404
            ));
        }
    }
}
