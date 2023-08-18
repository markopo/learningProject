package com.example.learningproject.services;

import com.example.learningproject.dto.BookMapper;
import com.example.learningproject.model.Book;
import com.example.learningproject.model.Course;
import com.example.learningproject.repository.CourseRepository;
import org.apache.tomcat.util.json.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final GoogleBooksService googleBooksService;

    private final BookService bookService;

    private final Logger logger = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    public CourseService(CourseRepository courseRepository,
                         GoogleBooksService googleBooksService,
                         BookService bookService) {
        this.courseRepository = courseRepository;
        this.googleBooksService = googleBooksService;
        this.bookService = bookService;
    }

    public List<Course> findAll() {
        return this.courseRepository.findAll();
    }

    public Optional<Course> findById(Integer id) {
        return this.courseRepository.findById(id);
    }

    public Course save(Course course) {
       return this.courseRepository.save(course);
    }

    public Course create(Course course, Boolean includeBooks) throws URISyntaxException, IOException, ParseException, InterruptedException {
        if(includeBooks) {
           course = this.saveBooksOnCourse(course);
        }

        return this.save(course);
    }

    public Course saveBooksOnCourse(Course course) throws URISyntaxException, IOException, InterruptedException, ParseException {
        var googleBooks = this.googleBooksService.Search(URLEncoder.encode(course.getTitle()));
        var booksUnsaved = googleBooks.stream().map(b -> BookMapper.mapToBook(b)).toList();
        List<Book> courseBooks = new ArrayList<>();

        for (Book book :
                booksUnsaved) {

            Book savedBook = null;

            try {
                savedBook = this.bookService.save(book);
            }
            catch (Exception exception) {
                logger.info(exception.getMessage());
                savedBook = null;
            }
            finally {
                if(savedBook != null) {
                    courseBooks.add(savedBook);
                }
            }
        }

        course.setBooks(courseBooks);
        return course;
    }

    public void delete(Course course) {
        this.courseRepository.delete(course);
    }
}
