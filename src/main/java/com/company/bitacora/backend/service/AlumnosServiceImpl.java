package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Alumnos;
import com.company.bitacora.backend.model.Authority;
import com.company.bitacora.backend.model.User;
import com.company.bitacora.backend.model.dao.AlumnosDao;
import com.company.bitacora.backend.model.dao.AuthorityDao;
import com.company.bitacora.backend.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.List;
import java.util.Optional;

@Service
public class AlumnosServiceImpl implements AlumnosService {

    @Autowired
    private AlumnosDao alumnosDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorityDao authorityDao;

    @Override
    public List<Alumnos> getAlumnos() {
        return (List<Alumnos>) alumnosDao.findAll();
    }

    @Override
    public Alumnos getAlumnoById(Long id) {
        Optional<Alumnos> alumnos= alumnosDao.findById(id);
        return alumnos.orElseThrow(()-> new RuntimeException("Alumno no encontrado"));
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Alumnos saveAlumno(Alumnos alumno) {
        // 1. Encriptar la contraseña con BCrypt (el prefijo {bcrypt} es añadido automáticamente)
        String encryptedPassword = passwordEncoder.encode(alumno.getContrasena());
        alumno.setContrasena(encryptedPassword);  // Guarda la contraseña en la tabla 'alumnos'

        // 2. Guardar el alumno en la tabla 'alumnos'
        Alumnos savedAlumno = alumnosDao.save(alumno);

        // 3. Crear y guardar el registro en la tabla 'users'
        User user = new User();
        user.setUsername(alumno.getCorreo());
        user.setPassword("{bcrypt}"+encryptedPassword);  // Usa la misma contraseña encriptada
        user.setEnabled(true);
        userDao.save(user);

        // 4. Crear y guardar el registro en la tabla 'authorities'
        Authority authority = new Authority();
        authority.setUser(user);  // Asigna el objeto User, no solo el correo
        authority.setAuthority("ROLE_ALUMNO");  // Asignar el rol 'ROLE_ALUMNO'
        authorityDao.save(authority);

        return savedAlumno;
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
    public boolean deleteAlumno(Long id) {
        if(alumnosDao.existsById(id)) {
            alumnosDao.deleteById(id);
            return true;
        }
        return false;
    }
}
