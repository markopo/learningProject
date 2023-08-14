package com.example.learningproject.dto;

import com.example.learningproject.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseDtoMapper {

    public static CourseDto mapCourse(Course course) {
        var books = course.getBooks();
        List<GoogleBooksDto> booksDtos = books != null ? books.stream().map(b -> BookMapper.mapToDto(b)).toList()
                : new ArrayList<>();

       return new CourseDto(course.getId(),
               course.getCourseCode(),
               course.getTitle(),
               course.getDescription(),
               booksDtos);
    }
}
