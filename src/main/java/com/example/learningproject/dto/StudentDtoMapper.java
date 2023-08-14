package com.example.learningproject.dto;

import com.example.learningproject.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDtoMapper {

    public static StudentDto mapStudentDto(Student student) {
        var courses = student.getCourses();
        List<CourseDto> coursesDto = courses != null ? courses.stream()
                .map(c -> CourseDtoMapper.mapCourse(c)).toList()
                : new ArrayList<>();
        return new StudentDto(student.getId(), student.getName(), student.getEmail(), coursesDto);
    }
}
