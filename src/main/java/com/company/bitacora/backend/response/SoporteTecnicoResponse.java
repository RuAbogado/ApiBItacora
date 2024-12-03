package com.company.bitacora.backend.response;

import com.company.bitacora.backend.model.SoporteTecnico;

import java.util.List;

public class SoporteTecnicoResponse {
    private List<SoporteTecnico> soporteTecnicos;

    public List<SoporteTecnico> getSoporteTecnicos() {
        return soporteTecnicos;
    }

    public void setSoporteTecnicos(List<SoporteTecnico> soporteTecnicos) {
        this.soporteTecnicos = soporteTecnicos;
    }
}
