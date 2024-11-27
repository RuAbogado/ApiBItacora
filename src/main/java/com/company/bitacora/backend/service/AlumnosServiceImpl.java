package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Alumnos;
import com.company.bitacora.backend.model.dao.AlumnosDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class AlumnosServiceImpl implements AlumnosService {

    @Autowired
    private AlumnosDao alumnosDao;

    @Override
    public List<Alumnos> getAlumnos() {
        return (List<Alumnos>) alumnosDao.findAll();
    }

    @Override
    public Alumnos getAlumnoById(Long id) {
        Optional<Alumnos> alumnos= alumnosDao.findById(id);
        return alumnos.orElseThrow(()-> new RuntimeException("Alumno no encontrado"));
    }

    @Override
    public Alumnos saveAlumno(Alumnos alumno) {
        return alumnosDao.save(alumno);
    }

    @Override
    public Alumnos updateAlumno(Long id, Alumnos alumno) {
        Alumnos alumnoNuevo= getAlumnoById(id);
        alumnoNuevo.setNombre(alumno.getNombre());
        alumnoNuevo.setApellido(alumno.getApellido());
        alumnoNuevo.setMatricula(alumno.getMatricula());
        alumnoNuevo.setCorreo(alumno.getCorreo());
        alumnoNuevo.setContrasena(alumno.getContrasena());
        return alumnosDao.save(alumnoNuevo);
    }

    @Override
    public void deleteAlumno(Long id) {
        alumnosDao.deleteById(id);
    }
}
