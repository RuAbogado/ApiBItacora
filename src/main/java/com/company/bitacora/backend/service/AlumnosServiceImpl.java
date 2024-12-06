package com.company.bitacora.backend.service;

import com.company.bitacora.backend.exceptio.AlumnoNotFoundException;
import com.company.bitacora.backend.model.Alumnos;
import com.company.bitacora.backend.model.Authority;
import com.company.bitacora.backend.model.User;
import com.company.bitacora.backend.model.dao.AlumnosDao;
import com.company.bitacora.backend.model.dao.AuthorityDao;
import com.company.bitacora.backend.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlumnosServiceImpl implements AlumnosService {

    @Autowired
    private AlumnosDao alumnosDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorityDao authorityDao;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<Alumnos> getAlumnos() {
        return (List<Alumnos>) alumnosDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Alumnos getAlumnoByCorreo(String correo) {
        if (correo == null || correo.isEmpty()) {
            throw new IllegalArgumentException("El correo no puede ser nulo o vacío");
        }
        return alumnosDao.findByCorreo(correo)
                .orElseThrow(() -> new AlumnoNotFoundException("Alumno con correo " + correo + " no encontrado"));
    }

    @Transactional
    @Override
    public Alumnos saveAlumno(Alumnos alumno) {
        if (alumnosDao.existsById(alumno.getCorreo())) {
            throw new IllegalArgumentException("Ya existe un alumno con el correo: " + alumno.getCorreo());
        }

        // Encriptar contraseña
        //String encryptedPassword = passwordEncoder.encode(alumno.getContrasena());
        //alumno.setContrasena(encryptedPassword);

        // Guardar alumno
        Alumnos savedAlumno = alumnosDao.save(alumno);

        // Crear y guardar usuario
        User user = new User();
        user.setUsername(alumno.getCorreo());
        user.setPassword(alumno.getContrasena());
        user.setEnabled(true);
        userDao.save(user);

        // Asociar o crear rol 'ROLE_ALUMNO'
        Authority authority = authorityDao.findByAuthority("ROLE_ALUMNO");
        if (authority == null) {
            authority = new Authority();
            authority.setAuthority("ROLE_ALUMNO");
            authorityDao.save(authority);
        }

        // Relacionar usuario y rol
        authority.getUsers().add(user);
        user.getAuthorities().add(authority);

        authorityDao.save(authority);
        userDao.save(user);

        return savedAlumno;
    }

    @Transactional
    @Override
    public Alumnos updateAlumno(String correo, Alumnos alumno) {
        Alumnos existingAlumno = getAlumnoByCorreo(correo);

        // Actualizar campos
        existingAlumno.setNombre(alumno.getNombre());
        existingAlumno.setApellido(alumno.getApellido());
        existingAlumno.setMatricula(alumno.getMatricula());

        // Actualizar contraseña si se proporciona
        if (alumno.getContrasena() != null && !alumno.getContrasena().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(alumno.getContrasena());
            existingAlumno.setContrasena(encryptedPassword);
        }

        return alumnosDao.save(existingAlumno);
    }

    @Transactional
    @Override
    public boolean deleteAlumno(String correo) {
        // Verifica si el alumno existe
        Alumnos alumno = alumnosDao.findByCorreo(correo)
                .orElseThrow(() -> new AlumnoNotFoundException("Alumno con correo " + correo + " no encontrado"));

        // Eliminar usuario asociado si existe
        User user = userDao.findByUsername(correo).orElse(null);
        if (user != null) {
            userDao.delete(user);
        }

        // Eliminar el alumno
        alumnosDao.delete(alumno);

        return true;
    }

}
