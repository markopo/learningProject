package com.example.learningproject.dto;

import java.util.List;

public record StudentDto(Integer id, String name, String email, List<CourseDto> courses) {
}
