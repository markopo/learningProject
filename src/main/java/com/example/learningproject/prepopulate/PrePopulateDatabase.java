package com.example.learningproject.prepopulate;

import com.example.learningproject.model.Course;
import com.example.learningproject.services.CourseService;
import com.example.learningproject.services.StudentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.example.learningproject.model.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
    public void createInitData() throws IOException {
        createStudent("marko", "marko@gmail.com");
        createStudent("kalle", "kalle@gmail.com");
        createStudent("pekka", "pekka@gmail.com");
        createStudent("jussi", "jussi@gmail.com");

        createCourse("java-001", "Java Programming", loadFromFile("java"));
        createCourse("csharp-001", "C# Programming", loadFromFile("csharp"));
        createCourse("javascript-001", "Javascript Programming", loadFromFile("javascript"));
        createCourse("sql-001", "SQL Programming", loadFromFile("sql"));
        createCourse("html5-001", "HTML 5 coding", loadFromFile("html5"));

        var courses = this.courseService.findAll();
        var students = this.studentService.findAll();

        for (Student student : students) {

            if(student.getName().equals("marko")) {

                List<Course> markosCourses = courses.stream().filter(x -> x.getCourseCode().equals("java-001")
                        || x.getCourseCode().equals("csharp-001")).toList();

                student.setCourses(markosCourses);
                this.studentService.save(student);

            }

            if(student.getName().equals("kalle")) {
                List<Course> kallesCourses = courses.stream().filter(x -> x.getCourseCode().equals("sql-001")).toList();

                student.setCourses(kallesCourses);
                this.studentService.save(student);
            }

            if(student.getName().equals("pekka")) {
                List<Course> pekkasCourses = courses.stream().filter(x -> x.getCourseCode().equals("html5-001")).toList();

                student.setCourses(pekkasCourses);
                this.studentService.save(student);
            }
        }


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

    private String loadFromFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/main/resources/" + fileName + ".txt")));
    }
}
