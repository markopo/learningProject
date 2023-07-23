package com.example.learningproject.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class StudentControllerTest {

    private static final String API_ROOT = "http://localhost:8080/api/students";

    @Test
    public void whenGetAllStudents_thenOK() {
      given()
          .auth()
          .basic("mary", "test123")
          .when()
          .get(API_ROOT)
          .then()
          .statusCode(200);
    }

    @Test
    public void whenGetById_thenOK() {
        given()
                .auth()
                .basic("mary", "test123")
                .when()
                .get(API_ROOT + "/1")
                .then()
                .contentType(ContentType.JSON)
                .body("name", equalTo("marko"))
                .statusCode(200);
    }


}
