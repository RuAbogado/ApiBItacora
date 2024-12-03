package com.company.bitacora.backend.response;

public class SalonResponseRest extends SalonResponse {
    private SalonResponse salonResponse = new SalonResponse();

    public SalonResponse getSalonResponse() {
        return salonResponse;
    }

    public void setSalonResponse(SalonResponse salonResponse) {
        this.salonResponse = salonResponse;
    }

}
