package com.company.bitacora.backend.response;

import com.company.bitacora.backend.model.Alumnos;

import java.util.List;

public class AlumnosResponse {
    private List<Alumnos> alumno;

    public List<Alumnos> getAlumno() {
        return alumno;
    }

    public void setAlumno(List<Alumnos> alumno) {
        this.alumno = alumno;
    }
}
