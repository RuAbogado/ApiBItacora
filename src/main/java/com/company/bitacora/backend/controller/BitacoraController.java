package com.company.bitacora.backend.controller;

import com.company.bitacora.backend.model.Bitacora;
import com.company.bitacora.backend.response.BitacoraResponseRest;
import com.company.bitacora.backend.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1")
public class BitacoraController {

    @Autowired
    private BitacoraService bitacoraService;

    @GetMapping("/bitacoras")
    public ResponseEntity<BitacoraResponseRest> getBitacoras() {
        return bitacoraService.buscarBitacora();
    }

    @GetMapping("/bitacoras/{id}")
    public ResponseEntity<BitacoraResponseRest> getBitacoraById(@PathVariable Long id) {
        return bitacoraService.buscarPorID(id);
    }

    @PostMapping("bitacoras")
    public ResponseEntity<BitacoraResponseRest> crear(@RequestBody Bitacora bitacora) {
        return bitacoraService.crearBitacora(bitacora);
    }

    @PutMapping("/bitacoras/{id}")
    public ResponseEntity<BitacoraResponseRest> actualizar(@RequestBody Bitacora bitacora, @PathVariable Long id) {
        return bitacoraService.actualizarBitacora(bitacora, id);
    }

    @PatchMapping("/bitacoras/{id}")
    public ResponseEntity<BitacoraResponseRest> upImg(@RequestParam("file") MultipartFile img, @PathVariable Long id) {
        ResponseEntity<BitacoraResponseRest> response = bitacoraService.upImagen(img, id);
        return response;
    }

    @DeleteMapping("/bitacoras/{id}")
    public ResponseEntity<BitacoraResponseRest> eliminar(@PathVariable Long id) {
        return bitacoraService.eliminarBitacora(id);
    }
}
