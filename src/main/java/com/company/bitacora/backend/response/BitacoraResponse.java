package com.company.bitacora.backend.response;

import com.company.bitacora.backend.model.Bitacora;
import java.util.List;

public class BitacoraResponse {
    // Lista de objetos del tipo Bitacora
    private List<Bitacora> bitacora;

    // Método getter para acceder a la lista de bitacoras
    public List<Bitacora> getBitacora() {
        return bitacora;
    }

    // Método setter para establecer la lista de bitacoras
    public void setBitacora(List<Bitacora> bitacora) {
        this.bitacora = bitacora;
    }
}
