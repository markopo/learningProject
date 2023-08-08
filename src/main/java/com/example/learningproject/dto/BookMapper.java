package com.example.learningproject.dto;

import com.example.learningproject.model.Book;

public class BookMapper {

    public static Book mapToBook(GoogleBooksDto googleBooksDto) {
        return new Book(googleBooksDto.title(), googleBooksDto.subTitle(), googleBooksDto.authors(),
                googleBooksDto.publisher(), googleBooksDto.publishedDate(), googleBooksDto.description(),
                googleBooksDto.isbn13(), googleBooksDto.isbn10());
    }

    public static GoogleBooksDto mapToDto(Book book) {
        return new GoogleBooksDto(book.getTitle(), book.getSubTitle(), book.getAuthors(),
                book.getPublisher(), book.getPublishedDate(), book.getDescription(), book.getIsbn13(), book.getIsbn10());
    }
}
