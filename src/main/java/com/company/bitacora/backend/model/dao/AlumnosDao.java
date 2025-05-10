package com.company.bitacora.backend.model.dao;

import com.company.bitacora.backend.model.Alumnos;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AlumnosDao extends CrudRepository<Alumnos, String> {
    Optional<Alumnos> findByCorreo(String correo);
}
