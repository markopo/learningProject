package com.example.learningproject.controllers;


import com.example.learningproject.dto.CourseDto;
import com.example.learningproject.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping("/courses")
    public List<CourseDto> getAllCourses() {
        return this.courseService.findAll().stream().map(x -> new CourseDto(x.getId(), x.getCourseCode(), x.getTitle())).toList();
    }
}
