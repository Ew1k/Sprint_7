import clients.OrderClient;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetOrdersTest {

    private final OrderClient orderClient = new OrderClient();

    @BeforeEach
    @Step("Подготовить URL API")
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("В ответе возвращается список заказов")
    public void getOrdersReturnsList() {

        Response response = getOrders();

        checkStatusCode(response, 200);
        checkOrdersList(response);
    }

    @Step("Получить список заказов")
    private Response getOrders() {
        return orderClient.getOrders();
    }

    @Step("Проверить код ответа: {expectedStatusCode}")
    private void checkStatusCode(Response response, int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.statusCode());
    }

    @Step("Проверить, что тело ответа содержит список заказов")
    private void checkOrdersList(Response response) {

        List<?> orders = response.jsonPath().getList("orders");

        assertNotNull(orders);
    }
}