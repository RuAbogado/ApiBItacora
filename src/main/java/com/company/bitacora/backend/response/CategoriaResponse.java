package com.company.bitacora.backend.response;

import com.company.bitacora.backend.model.Categoria;

import java.util.List;

public class CategoriaResponse {
    private List<Categoria> categoria;

    public List<Categoria> getCategoria() {
        return categoria;
    }

    public void setCategoria(List<Categoria> categoria) {
        this.categoria = categoria;
    }
}
