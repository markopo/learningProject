package com.example.learningproject.exceptions;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
