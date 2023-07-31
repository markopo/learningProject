package com.example.learningproject.controllers;

import com.example.learningproject.dto.StudentDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
      var response = given()
                .auth()
                .basic(USERNAME, PASSWORD)
                .when()
                .get(API_ROOT + "/name/marko")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200);

      List<StudentDto> list = response.extract().jsonPath().getList("$", StudentDto.class);
      assertTrue(list.get(0).name().equals("marko"));
    }

    @Test
    public void whenFindByEmail_thenOK() {
      var response =  given()
                .auth()
                .basic(USERNAME, PASSWORD)
                .when()
                .get(API_ROOT + "/email/marko@gmail.com")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200);

      List<StudentDto> list = response.extract().jsonPath().getList("$", StudentDto.class);
      assertTrue(list.get(0).name().equals("marko"));
    }

    @Test
    public void whenPost_thenOK() {

        String name = "banan-" + UUID.randomUUID();
        var email = name + "@gmail.com";

        given()
                .auth()
                .basic(USERNAME, PASSWORD)
                .when()
                .contentType(ContentType.JSON)
                .body(new StudentDto(0, name, email, null))
                .post(API_ROOT)
                .then()
                .contentType(ContentType.JSON)
                .body("name", equalTo(name))
                .statusCode(200);

       var response =  given()
            .auth()
            .basic(USERNAME, PASSWORD)
            .when()
            .get(API_ROOT + "/name/" + name)
            .then()
            .contentType(ContentType.JSON)
            .statusCode(200);

        List<StudentDto> list = response.extract().jsonPath().getList("$", StudentDto.class);
        assertTrue(list.get(0).name().equals(name));
    }

    @Test
    public void whenDelete_thenOK() {

        var name = "ola-conny-" + UUID.randomUUID();
        var email = name + "@gmail.com";

       var responseCreated =  given()
                                .auth()
                                .basic(USERNAME, PASSWORD)
                                .when()
                                .contentType(ContentType.JSON)
                                .body(new StudentDto(0, name, email, null))
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

        var name = "ola-conny-" + UUID.randomUUID();
        var email = name + "@gmail.com";

        var responseCreated =  given()
                .auth()
                .basic(USERNAME, PASSWORD)
                .when()
                .contentType(ContentType.JSON)
                .body(new StudentDto(0, name, email, null))
                .post(API_ROOT);

        var olaConny = responseCreated.body().as(StudentDto.class);

        var newName = "morgan-" + UUID.randomUUID();
        var newEmail = newName + "@gmail.com";

       var responseUpdated =  given()
                    .auth()
                    .basic(USERNAME, PASSWORD)
                    .when()
                    .contentType(ContentType.JSON)
                    .body(new StudentDto(olaConny.id(), newName, newEmail, null))
                    .put(API_ROOT + "/" + olaConny.id())
                    .then()
                    .statusCode(200);

       responseUpdated.body("name", equalTo(newName));
       responseUpdated.body("email", equalTo(newEmail));
       responseUpdated.body("id", equalTo(olaConny.id()));
    }


}
