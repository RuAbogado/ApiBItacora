package com.company.bitacora.backend.response;

import com.company.bitacora.backend.model.SoporteTecnico_Bitacora;

import java.util.List;

public class SoporteTecnico_BitacoraResponse {
    private List<SoporteTecnico_Bitacora> soporteTecnicoBitacoras;

    public List<SoporteTecnico_Bitacora> getSoporteTecnicoBitacoras() {
        return soporteTecnicoBitacoras;
    }

    public void setSoporteTecnicoBitacoras(List<SoporteTecnico_Bitacora> soporteTecnicoBitacoras) {
        this.soporteTecnicoBitacoras = soporteTecnicoBitacoras;
    }
}
