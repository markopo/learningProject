package com.example.learningproject.controllers;


import com.example.learningproject.dto.CourseDto;
import com.example.learningproject.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    private CourseRepository courseRepository;


    @Autowired
    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    public List<CourseDto> getAllCourses() {
        return this.courseRepository.findAll().stream().map(x -> new CourseDto(x.getCourseCode(), x.getTitle())).toList();
    }
}
