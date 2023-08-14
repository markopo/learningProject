package com.example.learningproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "SUBTITLE")
    private String subTitle;

    @Column(name = "AUTHORS")
    private String authors;

    @Column(name = "PUBLISHER")
    private String publisher;

    @Column(name = "PUBLISHED_DATE")
    private String publishedDate;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ISBN_13", unique = true)
    private String isbn13;

    @Column(name = "ISBN_10", unique = true)
    private String isbn10;


    public Book(String title, String subTitle, String authors, String publisher, String publishedDate, String description, String isbn13, String isbn10) {
        this.title = title;
        this.subTitle = subTitle;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.isbn13 = isbn13;
        this.isbn10 = isbn10;
    }

    public Book() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                "title=" + title +
                "subtitle=" + subTitle +
                "authors=" + authors +
                "publisher=" + publisher +
                "publishedDate=" + publishedDate +
                "description=" + description +
                "isbn13=" + isbn13 +
                "isbn10=" + isbn10 +
                "}";
    }

}
