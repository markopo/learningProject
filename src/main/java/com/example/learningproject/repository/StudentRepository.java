package com.example.learningproject.repository;

import com.example.learningproject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findStudentByName(String name);
    List<Student> findStudentByEmail(String email);
}
