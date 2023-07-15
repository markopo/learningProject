package com.example.learningproject.controllers;

import com.example.learningproject.dto.StudentDto;
import com.example.learningproject.exceptions.StudentNotFoundException;
import com.example.learningproject.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.learningproject.model.Student;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @GetMapping("/students")
    public List<StudentDto> getAllStudents() {
        return this.studentRepository.findAll().stream().map(x -> new StudentDto(x.getId(), x.getName(), x.getEmail())).toList();
    }

    @GetMapping("/students/{id}")
    public StudentDto findOne(@PathVariable Integer id) {
        var studentOptional = this.studentRepository.findById(id);

        if(studentOptional.isEmpty()) {
            throw new StudentNotFoundException("Student not found", null);
        }

        var student = studentOptional.get();
        return new StudentDto(student.getId(), student.getName(), student.getEmail());
    }

    @GetMapping("/students/name/{name}")
    public StudentDto findByName(@PathVariable String name) {
        var studentOptional = this.studentRepository.findStudentByName(name);

        if(studentOptional.isEmpty()) {
            throw new StudentNotFoundException("Student not found", null);
        }

        var student = studentOptional.get();
        return new StudentDto(student.getId(), student.getName(), student.getEmail());
    }

    @GetMapping("/students/email/{email}")
    public StudentDto findByEmail(@PathVariable String email) {
        var studentOptional = this.studentRepository.findStudentByEmail(email);

        if(studentOptional.isEmpty()) {
            throw new StudentNotFoundException("Student not found", null);
        }

        var student = studentOptional.get();
        return new StudentDto(student.getId(), student.getName(), student.getEmail());
    }


    @PostMapping("/students")
    public StudentDto create(@RequestBody StudentDto newStudent) {
        var createStudent = new Student(newStudent.name(), newStudent.email());
        createStudent.setId(0);
        var student = this.studentRepository.save(createStudent);
        return new StudentDto(student.getId(), student.getName(), student.getEmail());
    }
}
