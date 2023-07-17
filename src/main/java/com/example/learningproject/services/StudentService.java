package com.example.learningproject.services;

import com.example.learningproject.model.Student;
import com.example.learningproject.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> findAll() {
        return this.studentRepository.findAll();
    }

    public Optional<Student> findById(Integer id) {
       return this.studentRepository.findById(id);
    }

    public Optional<Student> findByName(String name) {
        return this.studentRepository.findStudentByName(name);
    }

    public Optional<Student> findByEmail(String email) {
        return this.studentRepository.findStudentByEmail(email);
    }

    public Student save(Student student) {
        return this.studentRepository.save(student);
    }

    public void delete(Student student) {
        this.studentRepository.save(student);
    }
}
