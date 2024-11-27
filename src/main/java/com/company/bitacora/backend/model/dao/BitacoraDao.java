package com.company.bitacora.backend.model.dao;

import com.company.bitacora.backend.model.Bitacora;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitacoraDao extends CrudRepository<Bitacora, Long> {
}
