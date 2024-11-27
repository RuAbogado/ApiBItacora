package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Alumnos;

import java.util.List;

public interface AlumnosService {
    List<Alumnos> getAlumnos();  //retorna todos los objetos de tipo Alumnos

    Alumnos getAlumnoById(Long id);   //busqueda de un alumno mediante el ID

    Alumnos saveAlumno(Alumnos alumno);   //se guarda un nuevo objeto de tipo Alumno

    Alumnos updateAlumno(Long id, Alumnos alumno);   //actualizacion de un objeto de tipo alumno mediante dos parametros

    void deleteAlumno(Long id); //se elimina un Alumno mediante el ID
}
