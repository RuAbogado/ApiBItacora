package com.company.bitacora.backend.response;

public class BitacoraResponseRest extends BitacoraResponse {
    private String additionalInfo;

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setMetadata(String respuestaOk, String number, String eliminaciónExitosa) {
    }
}
