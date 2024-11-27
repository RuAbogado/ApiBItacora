package com.company.bitacora.backend.response;

import java.util.ArrayList;
import java.util.HashMap;

public class ResponseRest {
    // Lista que contiene metadatos en forma de mapas (clave-valor)
    private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();

    // Método getter para obtener los metadatos
    public ArrayList<HashMap<String, String>> getMetadata() {
        return metadata;
    }

    // Método para agregar un mapa de metadatos a la lista
    public void setMetadata(String tipo, String codigo, String dato) {
        HashMap<String, String> mapa = new HashMap<>();
        mapa.put("tipo", tipo);
        mapa.put("codigo", codigo);
        mapa.put("dato", dato);
        metadata.add(mapa);
    }
}
