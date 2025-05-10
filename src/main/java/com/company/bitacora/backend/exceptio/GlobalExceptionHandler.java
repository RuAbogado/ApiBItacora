package com.company.bitacora.backend.exceptio;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.company.bitacora.backend.exceptio.AlumnoNotFoundException;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo específico para AlumnoNotFoundException
    @ExceptionHandler(AlumnoNotFoundException.class)
    public ResponseEntity<?> handleAlumnoNotFoundException(AlumnoNotFoundException ex) {
        // Devuelve una respuesta con código 404 y el mensaje de la excepción
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "message", ex.getMessage(),
                "codigo", 404
        ));
    }

    // Manejo de cualquier otra excepción no controlada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        // Devuelve una respuesta con código 500 para errores internos
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "message", "Error inesperado en el servidor",
                "codigo", 500
        ));
    }
}
