package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Categoria;
import com.company.bitacora.backend.response.CategoriaResponseRest;
import org.springframework.http.ResponseEntity;

public interface ICategoriaService {

    public ResponseEntity<CategoriaResponseRest> buscarCategorias();
    public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id);
    public ResponseEntity<CategoriaResponseRest> crear(Categoria categoria);
    public ResponseEntity<CategoriaResponseRest> actualizar(Categoria categoria, Long id);
    public ResponseEntity<CategoriaResponseRest> eliminar(Long id);
}
