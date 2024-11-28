package com.company.bitacora.backend.response;

public class BitacoraResponseRest extends BitacoraResponse {
    private BitacoraResponse bitacoraResponse= new BitacoraResponse();

    public BitacoraResponse getBitacoraResponse() {
        return bitacoraResponse;
    }
    public void setBitacoraResponse(BitacoraResponse bitacoraResponse) {
        this.bitacoraResponse = bitacoraResponse;
    }

    public void setMetadata(String respuestaNoActualizada, String s, String categoriaNoActualizada) {
    }
}
