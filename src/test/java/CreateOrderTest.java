import clients.OrderClient;
import clientsData.OrderData;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateOrderTest {

    private final OrderClient orderClient = new OrderClient();

    @BeforeEach
    @Step("Подготовить URL API")
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @ParameterizedTest(name = "Создание заказа: {0}")
    @MethodSource("orderData")
    @DisplayName("Можно создать заказ с разными вариантами цвета")
    public void createOrderWithDifferentColors(
            String colorDescription,
            Order order
    ) {

        Response response = createOrder(order);

        checkStatusCode(response, 201);
        checkResponseContainsTrack(response);
    }

    @Step("Создать заказ")
    private Response createOrder(Order order) {
        return orderClient.create(order);
    }

    @Step("Проверить код ответа: {expectedStatusCode}")
    private void checkStatusCode(
            Response response,
            int expectedStatusCode
    ) {
        assertEquals(expectedStatusCode, response.statusCode());
    }

    @Step("Проверить, что тело ответа содержит track")
    private void checkResponseContainsTrack(Response response) {

        int track = response.jsonPath().getInt("track");

        assertTrue(track > 0);
    }

    private static Stream<Arguments> orderData() {
        return OrderData.getOrdersWithDifferentColors();
    }
}