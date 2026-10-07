package clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import models.Courier;
import models.CourierCreds;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class CourierClient {

    private static final String CREATE_PATH = "/api/v1/courier";
    private static final String LOGIN_PATH = "/api/v1/courier/login";
    private static final String DELETE_PATH = "/api/v1/courier/";

    public Response create(Courier courier) {
        return given()
                .contentType(JSON)
                .and()
                .body(courier)
                .when()
                .post(CREATE_PATH);
    }

    public Response login(CourierCreds creds) {
        return given()
                .contentType(JSON)
                .and()
                .body(creds)
                .when()
                .post(LOGIN_PATH);
    }

    public Response delete(long id) {
        return given()
                .contentType(JSON)
                .and()
                .when()
                .delete(DELETE_PATH + id);
    }
}