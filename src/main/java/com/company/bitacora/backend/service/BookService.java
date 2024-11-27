package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book saveBook(Book book);
    Book updateBook(Long id, Book book);
    void deleteBook(Long id);
}

