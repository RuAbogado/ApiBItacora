package com.company.bitacora.backend.response;

import com.company.bitacora.backend.model.Equipo;

import java.util.List;

public class EquipoResponse {
    private List<Equipo> equipos;

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }
}
