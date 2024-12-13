package com.company.bitacora.backend.controller;

import com.company.bitacora.backend.model.Alumnos;
import com.company.bitacora.backend.service.AlumnosService;
import com.company.bitacora.backend.exceptio.AlumnoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AlumnosControllerTest {

    @Mock
    private AlumnosService alumnosService;

    @InjectMocks
    private AlumnosController alumnosController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void getAllAlumnos() {
        // Arrange
        Alumnos alumno1 = new Alumnos("Juan", "Pérez", "12345", "juan@example.com", "password123");
        Alumnos alumno2 = new Alumnos("Ana", "González", "67890", "ana@example.com", "password456");
        List<Alumnos> alumnosList = Arrays.asList(alumno1, alumno2);

        when(alumnosService.getAlumnos()).thenReturn(alumnosList);

        // Act
        ResponseEntity<?> response = alumnosController.getAllAlumnos();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Alumnos obtenidos con éxito"));
    }

    @Test
    void getAlumno_found() {
        // Arrange
        Alumnos alumno = new Alumnos("Juan", "Pérez", "12345", "juan@example.com", "password123");
        when(alumnosService.getAlumnoByCorreo("juan@example.com")).thenReturn(alumno);

        // Act
        ResponseEntity<?> response = alumnosController.getAlumno("juan@example.com");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Alumno obtenido con éxito"));
    }

    @Test
    void getAlumno_notFound() {
        // Arrange
        when(alumnosService.getAlumnoByCorreo("juan@example.com")).thenReturn(null);

        // Act
        ResponseEntity<?> response = alumnosController.getAlumno("juan@example.com");

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Alumno no encontrado"));
    }

    @Test
    void createAlumno() {
        // Arrange
        Alumnos alumno = new Alumnos("Juan", "Pérez", "12345", "juan@example.com", "password123");
        when(alumnosService.saveAlumno(any(Alumnos.class))).thenReturn(alumno);

        // Act
        ResponseEntity<?> response = alumnosController.createAlumno(alumno);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Alumno registrado con éxito"));
    }

    @Test
    void updateAlumno_success() {
        // Arrange
        Alumnos alumno = new Alumnos("Juan", "Pérez", "12345", "juan@example.com", "password123");
        when(alumnosService.updateAlumno(eq("juan@example.com"), any(Alumnos.class))).thenReturn(alumno);

        // Act
        ResponseEntity<?> response = alumnosController.updateAlumno("juan@example.com", alumno);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Alumno actualizado con éxito"));
    }

    @Test
    void updateAlumno_notFound() {
        // Arrange
        Alumnos alumno = new Alumnos("Juan", "Pérez", "12345", "juan@example.com", "password123");
        when(alumnosService.updateAlumno(eq("juan@example.com"), any(Alumnos.class)))
                .thenThrow(new AlumnoNotFoundException("Alumno no encontrado"));

        // Act
        ResponseEntity<?> response = alumnosController.updateAlumno("juan@example.com", alumno);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Alumno no encontrado"));
    }

    @Test
    void deleteAlumno_success() {
        // Arrange
        when(alumnosService.deleteAlumno("juan@example.com")).thenReturn(true);

        // Act
        ResponseEntity<?> response = alumnosController.deleteAlumno("juan@example.com");

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Alumno eliminado con éxito"));
    }

    @Test
    void deleteAlumno_notFound() {
        // Arrange
        when(alumnosService.deleteAlumno("juan@example.com")).thenThrow(new AlumnoNotFoundException("Alumno no encontrado"));

        // Act
        ResponseEntity<?> response = alumnosController.deleteAlumno("juan@example.com");

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Alumno no encontrado"));
    }
}
