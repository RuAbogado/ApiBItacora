package com.company.bitacora.backend.model.dao;

import com.company.bitacora.backend.model.Equipo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoDao extends CrudRepository<Equipo, Long> {
}
