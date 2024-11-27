package com.company.bitacora.backend.controller;

import com.company.bitacora.backend.model.Categoria;
import com.company.bitacora.backend.response.CategoriaResponseRest;
import com.company.bitacora.backend.service.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class CategoriaRestController {

    @Autowired
    private ICategoriaService service;

    @GetMapping("/categorias")
    public ResponseEntity<CategoriaResponseRest> consultaCat(){
        ResponseEntity<CategoriaResponseRest> response = service.buscarCategorias();
        return response;
    }
    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseRest> consultaPorId(@PathVariable Long id){
        ResponseEntity<CategoriaResponseRest> response = service.buscarPorId(id);
        return response;
    }
    @PostMapping("/categorias")
    public ResponseEntity<CategoriaResponseRest> crear(@RequestBody Categoria request){
        ResponseEntity<CategoriaResponseRest> response = service.crear(request);
        return response;
    }
    @PutMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseRest> actualizar(@RequestBody Categoria request, @PathVariable Long id){
        ResponseEntity<CategoriaResponseRest> response = service.actualizar(request, id);
        return response;
    }
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<CategoriaResponseRest> eliminar(@PathVariable Long id){
        ResponseEntity<CategoriaResponseRest> response = service.eliminar(id);
        return response;
    }
}
