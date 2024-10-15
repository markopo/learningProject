package com.example.learningproject.controllers;


import com.example.learningproject.dto.CourseDto;
import com.example.learningproject.dto.CourseDtoMapper;
import com.example.learningproject.exceptions.EntityNotFoundException;
import com.example.learningproject.model.Course;
import com.example.learningproject.services.CoursePdfGenerateService;
import com.example.learningproject.services.CourseService;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class CourseController {

    private final CourseService courseService;

    private final CoursePdfGenerateService coursePdfGenerateService;

    @Autowired
    public CourseController(CourseService courseService, CoursePdfGenerateService coursePdfGenerateService) {
        this.courseService = courseService;
        this.coursePdfGenerateService = coursePdfGenerateService;
    }


    @GetMapping("/courses")
    public List<CourseDto> getAllCourses() {
        return courseService.findAll().stream().map(x -> CourseDtoMapper.mapCourse(x)).toList();
    }

    @GetMapping("/courses/{id}")
    public CourseDto findOne(@PathVariable Integer id) {
        var courseOptional = courseService.findById(id);

        if (courseOptional.isEmpty()) {
            throw new EntityNotFoundException("Course not found", null);
        }

        var course = courseOptional.get();
        return CourseDtoMapper.mapCourse(course);
    }

    @GetMapping("/courses/pdf/{id}")
    public ResponseEntity<InputStreamResource> getCoursePdf(@PathVariable Integer id) {
        var courseOptional = courseService.findById(id);

        if (courseOptional.isEmpty()) {
            throw new EntityNotFoundException("Course not found", null);
        }

        var course = courseOptional.get();
        ByteArrayInputStream bis = this.coursePdfGenerateService.getPdf(course);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=" + course.getCourseCode() + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


    @PostMapping("/courses")
    public CourseDto create(@RequestBody CourseDto newCourse) throws URISyntaxException, IOException, ParseException, InterruptedException {
        var createdCourse = new Course(newCourse.courseCode(), newCourse.title(), newCourse.description());
        createdCourse.setId(0);
        var course = courseService.create(createdCourse, true);
        return CourseDtoMapper.mapCourse(course);
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
        return CourseDtoMapper.mapCourse(course);
    }

}
