package com.company.bitacora.backend.controller;


import com.company.bitacora.backend.model.SoporteTecnico;
import com.company.bitacora.backend.service.SoporteTecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tecnico")
public class SoporteTecnicoController {

    @Autowired
    private SoporteTecnicoService soporteTecnicoService;

    @GetMapping
    public ResponseEntity<List<SoporteTecnico>> getAll() {
        List<SoporteTecnico> tecnicos = soporteTecnicoService.listarTecnicos();
        return ResponseEntity.ok(tecnicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoporteTecnico> getById(@PathVariable Long id) {
        SoporteTecnico tecnico = soporteTecnicoService.getSoporteTecnico(id);
        return ResponseEntity.ok(tecnico);
    }

    @PostMapping
    public ResponseEntity<SoporteTecnico> create(@RequestBody SoporteTecnico soporteTecnico) {
        SoporteTecnico newTecnico = soporteTecnicoService.saveSoporteTecnico(soporteTecnico);
        return ResponseEntity.ok(newTecnico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SoporteTecnico> updateTecnico(@PathVariable Long id, @RequestBody SoporteTecnico soporteTecnico) {
        SoporteTecnico updateTecnico = soporteTecnicoService.updateSoporteTecnico(id,soporteTecnico );
        return ResponseEntity.ok(updateTecnico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SoporteTecnico> deleteTecnico(@PathVariable Long id) {
        soporteTecnicoService.deleteSoporteTecnico(id);
        return ResponseEntity.noContent().build();
    }
}