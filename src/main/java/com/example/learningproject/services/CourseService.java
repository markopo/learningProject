package com.example.learningproject.services;

import com.example.learningproject.model.Course;
import com.example.learningproject.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return this.courseRepository.findAll();
    }

    public Optional<Course> findById(Integer id) {
        return this.courseRepository.findById(id);
    }

    public Course save(Course course) {
       return this.courseRepository.save(course);
    }

    public void delete(Course course) {
        this.courseRepository.delete(course);
    }
}
