package com.example.learningproject.dto;

import com.example.learningproject.model.Student;

public class CourseDtoMapper {

    public static StudentDto mapStudentDto(Student student) {
        var courses = student.getCourses().stream().map(c -> new CourseDto(c.getId(), c.getCourseCode(), c.getTitle(), c.getDescription())).toList();
        return new StudentDto(student.getId(), student.getName(), student.getEmail(), courses);
    }
}
