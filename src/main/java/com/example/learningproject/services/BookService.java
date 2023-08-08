package com.example.learningproject.services;

import com.example.learningproject.model.Book;
import com.example.learningproject.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    public Optional<Book> findById(Integer id) {
        return this.bookRepository.findById(id);
    }

    public Book save(Book book) {
        return this.bookRepository.save(book);
    }

    public void delete(Book book) {
        this.bookRepository.delete(book);
    }

}
