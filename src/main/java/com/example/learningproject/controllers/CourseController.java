package com.example.learningproject.controllers;


import com.example.learningproject.dto.CourseDto;
import com.example.learningproject.exceptions.EntityNotFoundException;
import com.example.learningproject.model.Course;
import com.example.learningproject.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return courseService.findAll().stream().map(x -> new CourseDto(x.getId(), x.getCourseCode(), x.getTitle(), x.getDescription())).toList();
    }

    @GetMapping("/courses/{id}")
    public CourseDto findOne(@PathVariable Integer id) {
        var courseOptional = courseService.findById(id);

        if (courseOptional.isEmpty()) {
            throw new EntityNotFoundException("Course not found", null);
        }

        var course = courseOptional.get();
        return new CourseDto(course.getId(), course.getCourseCode(), course.getTitle(), course.getDescription());
    }

    @PostMapping("/courses")
    public CourseDto create(@RequestBody CourseDto newCourse) {
        var createdCourse = new Course(newCourse.courseCode(), newCourse.title(), newCourse.description());
        createdCourse.setId(0);
        var course = courseService.save(createdCourse);
        return new CourseDto(course.getId(), course.getCourseCode(), course.getTitle(), course.getDescription());
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        var courseOptional = courseService.findById(id);

        if (courseOptional.isEmpty()) {
            throw new EntityNotFoundException("Course not found", null);
        }

        var course = courseOptional.get();
        this.courseService.delete(course);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/courses/{id}")
    public CourseDto updateCourse(@RequestBody CourseDto courseDto, @PathVariable Integer id) {
        var courseOptional = this.courseService.findById(id);

        if (courseOptional.isEmpty()) {
            throw new EntityNotFoundException("Course not found", null);
        }

        var course = courseOptional.get();
        course.setCourseCode(courseDto.courseCode());
        course.setTitle(courseDto.title());
        course.setDescription(courseDto.description());
        course = this.courseService.save(course);
        return new CourseDto(course.getId(), course.getCourseCode(), course.getTitle(), course.getDescription());
    }

}
