package com.example.learningproject.controllers;

import com.example.learningproject.dto.StudentDto;
import com.example.learningproject.exceptions.EntityNotFoundException;
import com.example.learningproject.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.learningproject.model.Student;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentService studentService;

    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/students")
    public List<StudentDto> getAllStudents() {
        logger.info("getAllStudents!");
        return studentService.findAll().stream().map(x -> new StudentDto(x.getId(), x.getName(), x.getEmail())).toList();
    }

    @GetMapping("/students/{id}")
    public StudentDto findOne(@PathVariable Integer id) {
        logger.info("findOne =" + id);
        var studentOptional = studentService.findById(id);

        if(studentOptional.isEmpty()) {
            throw new EntityNotFoundException("Student not found", null);
        }

        var student = studentOptional.get();
        return new StudentDto(student.getId(), student.getName(), student.getEmail());
    }

    @GetMapping("/students/name/{name}")
    public StudentDto findByName(@PathVariable String name) {
        logger.info("findByName = " + name);
        var studentOptional = this.studentService.findByName(name);

        if(studentOptional.isEmpty()) {
            throw new EntityNotFoundException("Student not found", null);
        }

        var student = studentOptional.get();
        return new StudentDto(student.getId(), student.getName(), student.getEmail());
    }

    @GetMapping("/students/email/{email}")
    public StudentDto findByEmail(@PathVariable String email) {
        logger.info("findByEmail = " + email);
        var studentOptional = this.studentService.findByEmail(email);

        if(studentOptional.isEmpty()) {
            throw new EntityNotFoundException("Student not found", null);
        }

        var student = studentOptional.get();
        return new StudentDto(student.getId(), student.getName(), student.getEmail());
    }


    @PostMapping("/students")
    public StudentDto create(@RequestBody StudentDto newStudent) {
        var createStudent = new Student(newStudent.name(), newStudent.email());
        createStudent.setId(0);
        var student = this.studentService.save(createStudent);
        logger.info("created student, id = " + student.getId());
        return new StudentDto(student.getId(), student.getName(), student.getEmail());
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
       var studentOptional = this.studentService.findById(id);

        if(studentOptional.isEmpty()) {
            throw new EntityNotFoundException("Student not found", null);
        }

        var student = studentOptional.get();
        this.studentService.delete(student);
        logger.info("deleted student, id = " + id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/students/{id}")
    public StudentDto updateStudent(@RequestBody StudentDto studentDto, @PathVariable Integer id) {
        var studentOptional = this.studentService.findById(id);

        if(studentOptional.isEmpty()) {
            throw new EntityNotFoundException("Student not found", null);
        }

        var student = studentOptional.get();
        student.setName(studentDto.name());
        student.setEmail(studentDto.email());
        student = this.studentService.save(student);
        logger.info("updated student, id = " + student.getId());
        return new StudentDto(student.getId(), student.getName(), student.getEmail());
    }
}
