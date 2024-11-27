package com.company.bitacora.backend.model.dao;

import com.company.bitacora.backend.model.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface ICategoriaDao extends CrudRepository<Categoria, Long> {

}
