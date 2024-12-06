package com.company.bitacora.backend.exceptio;

public class AlumnoNotFoundException extends RuntimeException {

    public AlumnoNotFoundException(String message) {
        super(message);
    }

    public AlumnoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
