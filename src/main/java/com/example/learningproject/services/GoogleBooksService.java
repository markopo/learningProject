package com.example.learningproject.services;

import com.example.learningproject.dto.GoogleBooksDto;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class GoogleBooksService {

    private final String baseUrl = "https://www.googleapis.com/books/v1/volumes?q=";

    private final Logger logger = LoggerFactory.getLogger(GoogleBooksService.class);
    public List<GoogleBooksDto> Search(String search) throws URISyntaxException, IOException, InterruptedException, ParseException {
        String url = baseUrl + search;
        var request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        var clientBuilder = HttpClient.newBuilder();
        var client = clientBuilder.build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var body = response.body();
        logger.info("search" + url);
        logger.info("response: " + body);
        return parseJson(body);
    }

    private List<GoogleBooksDto> parseJson(String body) throws ParseException {
        ArrayList<GoogleBooksDto> googleBooksDtoArrayList;

        JSONParser parser = new JSONParser(body);
        LinkedHashMap json = (LinkedHashMap)parser.parse();
        ArrayList items = (ArrayList)json.get("items");
        logger.info("items: " + items.size());
        googleBooksDtoArrayList = new ArrayList<>(items.size());

        for(Object item : items) {
            try {
                LinkedHashMap theItem = (LinkedHashMap) item;
                LinkedHashMap volumeInfo = (LinkedHashMap) theItem.get("volumeInfo");

                String title = (String) volumeInfo.get("title");
                String subTitle = (String) volumeInfo.get("subtitle");
                String authors = String.join(", ", (ArrayList<String>) volumeInfo.get("authors"));
                String publisher = (String) volumeInfo.get("publisher");
                String publishedDate = (String) volumeInfo.get("publishedDate");
                String description = (String) volumeInfo.get("description");

                ArrayList industryIdentifiers = (ArrayList) volumeInfo.get("industryIdentifiers");

                String isbn10 = "";
                String isbn13 = "";

                for (Object isbn : industryIdentifiers) {
                    LinkedHashMap theIsbn = (LinkedHashMap) isbn;
                    String type = (String) theIsbn.get("type");
                    if (type.equals("ISBN_10")) {
                        isbn10 = (String) theIsbn.get("identifier");
                    } else if (type.equals("ISBN_13")) {
                        isbn13 = (String) theIsbn.get("identifier");
                    }
                }

                googleBooksDtoArrayList
                .add(new GoogleBooksDto(title, subTitle, authors,
                        publisher, publishedDate, description, isbn13, isbn10));
            }
            catch(Exception exception) {
                logger.error("JSON parsing error: " + exception.getMessage());
            }
        }

        return googleBooksDtoArrayList;
    }
}
