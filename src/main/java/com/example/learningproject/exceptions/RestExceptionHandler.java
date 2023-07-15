package com.example.learningproject.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ StudentNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(Exception exception, WebRequest request) {
        return handleExceptionInternal(exception, "Student not found", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


}
