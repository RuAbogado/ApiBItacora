package com.company.bitacora.backend.response;

public class AlumnosResponseRest extends ResponseRest {
    private AlumnosResponse alumnosResponse = new AlumnosResponse();

    public AlumnosResponse getAlumnosResponse() {
        return alumnosResponse;
    }

    public void setAlumnosResponse(AlumnosResponse alumnosResponse) {
        this.alumnosResponse = alumnosResponse;
    }
}
