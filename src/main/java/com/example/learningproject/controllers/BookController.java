package com.example.learningproject.controllers;

import com.example.learningproject.dto.BookMapper;
import com.example.learningproject.dto.GoogleBooksDto;
import com.example.learningproject.exceptions.EntityNotFoundException;
import com.example.learningproject.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/books")
    public List<GoogleBooksDto> getAllBooks() {
        return this.bookService.findAll().stream().map(b -> BookMapper.mapToDto(b)).toList();
    }

    @GetMapping("/books/{id}")
    public GoogleBooksDto findOne(@PathVariable Integer id) {
        var bookOptional = this.bookService.findById(id);

        if(bookOptional.isEmpty()) {
            throw new EntityNotFoundException("Book not found!", null);
        }

        var book = bookOptional.get();
        return BookMapper.mapToDto(book);
    }


}
