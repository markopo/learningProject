package com.example.learningproject.controllers;

import com.example.learningproject.dto.GoogleBooksDto;
import com.example.learningproject.exceptions.EntityNotFoundException;
import com.example.learningproject.services.GoogleBooksService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.json.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GoogleBooksController {

    private final GoogleBooksService googleBooksService;

    private final Logger logger = LoggerFactory.getLogger(GoogleBooksController.class);
    @Autowired
    public GoogleBooksController(GoogleBooksService googleBooksService) {
        this.googleBooksService = googleBooksService;
    }

    @GetMapping("/search")
    public List<GoogleBooksDto> search(HttpServletRequest request) throws URISyntaxException, IOException, InterruptedException, ParseException {
        var queryString = request.getQueryString();
        logger.info("querystring: " + queryString);
        var qParams = request.getParameterValues("q");

        if(qParams == null) {
            throw new EntityNotFoundException("Q is not provided!", null);
        }

        String q = URLEncoder.encode(String.join(" ", qParams), StandardCharsets.UTF_8.toString());
        logger.info("Search: " + q);

        if(q.isEmpty()) {
            throw new EntityNotFoundException("Q is empty!", null);
        }

        return this.googleBooksService.Search(q);
    }

}
