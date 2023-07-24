package com.example.learningproject.controllers;

import com.example.learningproject.dto.StudentDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class StudentControllerTest {

    private static final String API_ROOT = "http://localhost:8080/api/students";

    private static final String USERNAME = "mary";

    private static final String PASSWORD = "test123";

    @Test
    public void whenGetAllStudents_thenOK() {
      given()
          .auth()
          .basic(USERNAME, PASSWORD)
          .when()
          .get(API_ROOT)
          .then()
          .statusCode(200);
    }

    @Test
    public void whenGetById_thenOK() {
        given()
                .auth()
                .basic(USERNAME, PASSWORD)
                .when()
                .get(API_ROOT + "/1")
                .then()
                .contentType(ContentType.JSON)
                .body("name", equalTo("marko"))
                .statusCode(200);
    }

    @Test
    public void whenFindByNamethenOK() {
        given()
                .auth()
                .basic(USERNAME, PASSWORD)
                .when()
                .get(API_ROOT + "/name/marko")
                .then()
                .contentType(ContentType.JSON)
                .body("name", equalTo("marko"))
                .statusCode(200);
    }

    @Test
    public void whenFindByEmail_thenOK() {
        given()
                .auth()
                .basic(USERNAME, PASSWORD)
                .when()
                .get(API_ROOT + "/email/marko@gmail.com")
                .then()
                .contentType(ContentType.JSON)
                .body("name", equalTo("marko"))
                .statusCode(200);
    }

    @Test
    public void whenPost_thenOK() {
        given()
                .auth()
                .basic(USERNAME, PASSWORD)
                .when()
                .contentType(ContentType.JSON)
                .body(new StudentDto(0, "banan", "banan@gmail.com"))
                .post(API_ROOT)
                .then()
                .contentType(ContentType.JSON)
                .body("name", equalTo("banan"))
                .statusCode(200);

        given()
            .auth()
            .basic(USERNAME, PASSWORD)
            .when()
            .get(API_ROOT + "/name/banan")
            .then()
            .contentType(ContentType.JSON)
            .body("name", equalTo("banan"))
            .statusCode(200);
    }

    @Test
    public void whenDelete_thenOK() {
       var responseCreated =  given()
                                .auth()
                                .basic(USERNAME, PASSWORD)
                                .when()
                                .contentType(ContentType.JSON)
                                .body(new StudentDto(0, "ola-conny", "ola-conny@gmail.com"))
                                .post(API_ROOT);

        var olaConny = responseCreated.body().as(StudentDto.class);

        given()
                .auth()
                .basic("susan", PASSWORD)
                .when()
                .delete(API_ROOT + "/" + olaConny.id())
                .then()
                .statusCode(200);
    }

    @Test
    public void whenPut_thenOK() {
        var responseCreated =  given()
                .auth()
                .basic(USERNAME, PASSWORD)
                .when()
                .contentType(ContentType.JSON)
                .body(new StudentDto(0, "ola-conny", "ola-conny@gmail.com"))
                .post(API_ROOT);

        var olaConny = responseCreated.body().as(StudentDto.class);

       var responseUpdated =  given()
                    .auth()
                    .basic(USERNAME, PASSWORD)
                    .when()
                    .contentType(ContentType.JSON)
                    .body(new StudentDto(olaConny.id(), "morgan", "morgan@gmail.com"))
                    .put(API_ROOT + "/" + olaConny.id())
                    .then()
                    .statusCode(200);

       responseUpdated.body("name", equalTo("morgan"));
       responseUpdated.body("email", equalTo("morgan@gmail.com"));
       responseUpdated.body("id", equalTo(olaConny.id()));
    }


}
