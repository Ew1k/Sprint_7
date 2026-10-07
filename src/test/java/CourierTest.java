import clients.CourierClient;
import clientsData.CourierData;
import clientsData.GetCourierId;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Courier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CourierTest {

    private final CourierClient courierClient = new CourierClient();
    private final GetCourierId getCourierId = new GetCourierId();

    private Courier courier;
    private long courierId;

    @BeforeEach
    @Step("Подготовить URL API")
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Можно создать курьера")
    public void courierCanBeCreated() {

        courier = getCourierData();

        Response response = createCourier();

        checkStatusCode(response, 201);
        checkResponseBody(response);

        courierId = getCourierId(courier);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void cannotCreateTwoIdenticalCouriers() {

        courier = getCourierData();

        createCourier();

        Response secondResponse = createCourier();

        checkStatusCode(secondResponse, 409);

        courierId = getCourierId(courier);
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина")
    public void cannotCreateCourierWithoutLogin() {

        courier = CourierData.getCourierWithoutLogin();

        Response response = createCourier();

        checkStatusCode(response, 400);
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля")
    public void cannotCreateCourierWithoutPassword() {

        courier = CourierData.getCourierWithoutPassword();

        Response response = createCourier();

        checkStatusCode(response, 400);
    }

    @Test
    @DisplayName("Нельзя создать курьера без имени")
    public void cannotCreateCourierWithoutFirstName() {

        courier = CourierData.getCourierWithoutFirstName();

        Response response = createCourier();

        checkStatusCode(response, 400);
    }

    @Step("Получить тестовые данные курьера")
    private Courier getCourierData() {
        return CourierData.getCourier();
    }

    @Step("Создать курьера")
    private Response createCourier() {
        return courierClient.create(courier);
    }

    @Step("Получить ID созданного курьера")
    private long getCourierId(Courier courier) {
        return getCourierId.getCourierId(courier);
    }

    @Step("Проверить код ответа: {expectedStatusCode}")
    private void checkStatusCode(Response response, int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.statusCode());
    }

    @Step("Проверить тело успешного ответа: ok = true")
    private void checkResponseBody(Response response) {
        assertTrue(response.jsonPath().getBoolean("ok"));
    }

    @AfterEach
    @Step("Удалить созданного курьера")
    public void tearDown() {

        if (courierId > 0) {
            courierClient.delete(courierId);
        }
    }
}