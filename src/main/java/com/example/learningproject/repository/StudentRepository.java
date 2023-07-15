package com.example.learningproject.repository;

import com.example.learningproject.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findStudentByName(String name);
    Optional<Student> findStudentByEmail(String email);
}
