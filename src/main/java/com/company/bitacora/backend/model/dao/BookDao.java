package com.company.bitacora.backend.model.dao;

import com.company.bitacora.backend.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDao extends CrudRepository<Book, Long> {
    // El CrudRepository ya proporciona métodos CRUD básicos
}

