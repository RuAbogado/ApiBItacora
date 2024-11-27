package com.company.bitacora.backend.controller;

import com.company.bitacora.backend.model.Bitacora;
import com.company.bitacora.backend.response.BitacoraResponseRest;
import com.company.bitacora.backend.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bitacoras")
public class BitacoraController {

    @Autowired
    private BitacoraService bitacoraService;

    @GetMapping
    public ResponseEntity<BitacoraResponseRest> getBitacoras() {
        return bitacoraService.buscarBitacora();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BitacoraResponseRest> getBitacoraById(@PathVariable Long id) {
        return bitacoraService.buscarPorID(id);
    }

    @PostMapping
    public ResponseEntity<BitacoraResponseRest> crear(@RequestBody Bitacora bitacora) {
        return bitacoraService.crearBitacora(bitacora);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BitacoraResponseRest> actualizar(@RequestBody Bitacora bitacora, @PathVariable Long id) {
        return bitacoraService.actualizarBitacora(bitacora, id);
    }

    @PatchMapping("/bitacora/{}")

    @DeleteMapping("/{id}")
    public ResponseEntity<BitacoraResponseRest> eliminar(@PathVariable Long id) {
        return bitacoraService.eliminarBitacora(id);
    }
}