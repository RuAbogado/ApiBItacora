package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Alumnos;

import java.util.List;

public interface AlumnosService {
    List<Alumnos> getAlumnos();

    Alumnos getAlumnoByCorreo(String correo);

    Alumnos saveAlumno(Alumnos alumno);

    Alumnos updateAlumno(String correo, Alumnos alumno);

    boolean deleteAlumno(String correo);
}
