package com.company.bitacora.backend.controller;


import com.company.bitacora.backend.model.SoporteTecnico_Bitacora;
import com.company.bitacora.backend.service.SoporteTecnico_BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bitacora_tecnico")
public class SoporteTecnico_BitacoraController {

    @Autowired
    private SoporteTecnico_BitacoraService soporteTecnicoBitacoraService;

    @GetMapping
    public ResponseEntity<List<SoporteTecnico_Bitacora>> getAll() {
        List<SoporteTecnico_Bitacora> tecnicos = soporteTecnicoBitacoraService.BitacorasTecnicos();
        return ResponseEntity.ok(tecnicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoporteTecnico_Bitacora> getById(@PathVariable Long id) {
        SoporteTecnico_Bitacora tecnico_bitacora = soporteTecnicoBitacoraService.BuscarBitacora(id);
        return ResponseEntity.ok(tecnico_bitacora);
    }

    @PostMapping
    public ResponseEntity<SoporteTecnico_Bitacora> createBitacoraTecnico(@RequestBody SoporteTecnico_Bitacora SoporteTecnico_Bitacora) {
        SoporteTecnico_Bitacora newBitacora_tecnico = soporteTecnicoBitacoraService.NuevaBitacora(SoporteTecnico_Bitacora);
        return ResponseEntity.ok(newBitacora_tecnico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SoporteTecnico_Bitacora> updateBitacoraTecnico(@PathVariable Long id, @RequestBody SoporteTecnico_Bitacora SoporteTecnico_Bitacora) {
        SoporteTecnico_Bitacora updateBitacora_Tecnico = soporteTecnicoBitacoraService.actualizarBitacora(id, SoporteTecnico_Bitacora);
        return ResponseEntity.ok(updateBitacora_Tecnico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBitacoraTecnico(@PathVariable Long id) {
        soporteTecnicoBitacoraService.eliminarBitacora(id);
        return ResponseEntity.noContent().build();
    }

}
