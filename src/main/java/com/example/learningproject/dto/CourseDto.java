package com.example.learningproject.dto;

import java.util.List;

public record CourseDto(Integer id, String courseCode, String title, String description, List<GoogleBooksDto> books) {
}
