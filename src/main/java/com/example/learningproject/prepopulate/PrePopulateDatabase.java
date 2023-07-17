package com.example.learningproject.prepopulate;

import com.example.learningproject.model.Course;
import com.example.learningproject.services.CourseService;
import com.example.learningproject.services.StudentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.example.learningproject.model.Student;

@Configuration
public class PrePopulateDatabase {

    private final StudentService studentService;

    private final CourseService courseService;

    @Autowired
    public PrePopulateDatabase(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @PostConstruct
    public void createInitData() {
        createStudent("marko", "marko@gmail.com");
        createStudent("kalle", "kalle@gmail.com");
        createStudent("pekka", "pekka@gmail.com");
        createStudent("jussi", "jussi@gmail.com");

        createCourse("java-001", "Java Programming", "Java..");
        createCourse("csharp-001", "C# Programming", "C# ...");
        createCourse("javascript-001", "Javascript Programming", "JS ...");
        createCourse("sql-001", "SQL Programming", "SQL ...");

    }

    private void createStudent(String name, String email) {
        var student = new Student();
        student.setEmail(email);
        student.setName(name);
        this.studentService.save(student);
    }

    private void createCourse(String courseCode, String title, String description) {
        var course = new Course();
        course.setCourseCode(courseCode);
        course.setTitle(title);
        course.setDescription(description);
        this.courseService.save(course);
    }
}
