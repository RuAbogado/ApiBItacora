package com.company.bitacora.backend.model.dao;

import com.company.bitacora.backend.model.SoporteTecnico;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SoporteTecnicoDao extends CrudRepository<SoporteTecnico, Long> {
    Optional<SoporteTecnico> findByCorreo(String correo);
}
