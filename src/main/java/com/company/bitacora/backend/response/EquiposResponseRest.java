package com.company.bitacora.backend.response;

public class EquiposResponseRest extends ResponseRest {
    private EquipoResponse equipoResponse = new EquipoResponse();

    public EquipoResponse getEquipoResponse() {
        return equipoResponse;
    }

    public void setEquipoResponse(EquipoResponse equipoResponse) {
        this.equipoResponse = equipoResponse;
    }
}
