package com.example.learningproject.controllers;

import com.example.learningproject.dto.StudentDto;
import com.example.learningproject.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {

    private StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @GetMapping("/students")
    public List<StudentDto> getAllStudents() {
        return this.studentRepository.findAll().stream().map(x -> new StudentDto(x.getName(), x.getEmail())).toList();
    }
}
