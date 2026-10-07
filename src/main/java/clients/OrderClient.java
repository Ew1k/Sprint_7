package clients;

import io.restassured.response.Response;
import models.Order;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class OrderClient {

    private static final String CREATE_PATH = "/api/v1/orders";
    private static final String ORDERS_PATH = "/api/v1/orders";

    public Response create(Order order) {
        return given()
                .contentType(JSON)
                .body(order)
                .when()
                .post(CREATE_PATH);
    }

    public Response getOrders() {
        return given()
                .when()
                .get(ORDERS_PATH);
    }
}