package com.company.bitacora.backend.service;

import com.company.bitacora.backend.model.dao.BookDao;
import com.company.bitacora.backend.model.Book;
import com.company.bitacora.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> getAllBooks() {
        return (List<Book>) bookDao.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> book = bookDao.findById(id);
        return book.orElseThrow(() -> new RuntimeException("Libro no encontrado"));
    }

    @Override
    public Book saveBook(Book book) {
        return bookDao.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        Book existingBook = getBookById(id);
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbn(book.getIsbn());
        return bookDao.save(existingBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookDao.deleteById(id);
    }
}

