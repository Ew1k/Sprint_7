import clients.CourierClient;
import clientsData.CourierData;
import clientsData.GetCourierId;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Courier;
import models.CourierCreds;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginCourierTest {

    private final CourierClient courierClient = new CourierClient();
    private final GetCourierId getCourierId = new GetCourierId();

    private Courier courier;
    private long courierId;

    @BeforeEach
    @Step("Подготовить URL API")
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        courier = getCourierData();

        createCourier();
        courierId = getCourierId(courier);
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    public void courierCanLogin() {

        Response response = login();

        checkStatusCode(response, 200);
        checkResponseContainsId(response);
    }

    @Test
    @DisplayName("Нельзя авторизоваться без логина")
    public void cannotLoginWithoutLogin() {

        CourierCreds credentials = new CourierCreds(
                null,
                courier.getPassword()
        );

        Response response = login(credentials);

        checkStatusCode(response, 400);
    }

    @Test
    @DisplayName("Нельзя авторизоваться без пароля")
    public void cannotLoginWithoutPassword() {

        CourierCreds credentials = new CourierCreds(
                courier.getLogin(),
                null
        );

        Response response = login(credentials);

        checkStatusCode(response, 400);
    }

    @Test
    @DisplayName("Нельзя авторизоваться с неправильным логином")
    public void cannotLoginWithWrongLogin() {

        CourierCreds credentials = new CourierCreds(
                courier.getLogin() + "_wrong",
                courier.getPassword()
        );

        Response response = login(credentials);

        checkStatusCode(response, 404);
    }

    @Test
    @DisplayName("Нельзя авторизоваться с неправильным паролем")
    public void cannotLoginWithWrongPassword() {

        CourierCreds credentials = new CourierCreds(
                courier.getLogin(),
                courier.getPassword() + "_wrong"
        );

        Response response = login(credentials);

        checkStatusCode(response, 404);
    }

    @Test
    @DisplayName("Нельзя авторизоваться под несуществующим пользователем")
    public void cannotLoginAsNonExistentCourier() {

        Courier nonExistentCourier = CourierData.getCourier();

        CourierCreds credentials = CourierData.getCredentials(
                nonExistentCourier
        );

        Response response = login(credentials);

        checkStatusCode(response, 404);
    }

    @Step("Получить тестовые данные курьера")
    private Courier getCourierData() {
        return CourierData.getCourier();
    }

    @Step("Создать тестового курьера")
    private Response createCourier() {
        return courierClient.create(courier);
    }

    @Step("Получить ID тестового курьера")
    private long getCourierId(Courier courier) {
        return getCourierId.getCourierId(courier);
    }

    @Step("Авторизоваться под тестовым курьером")
    private Response login() {
        return courierClient.login(
                CourierData.getCredentials(courier)
        );
    }

    @Step("Авторизоваться с переданными данными")
    private Response login(CourierCreds credentials) {
        return courierClient.login(credentials);
    }

    @Step("Проверить код ответа: {expectedStatusCode}")
    private void checkStatusCode(
            Response response,
            int expectedStatusCode
    ) {
        assertEquals(expectedStatusCode, response.statusCode());
    }

    @Step("Проверить, что ответ содержит ID курьера")
    private void checkResponseContainsId(Response response) {
        long id = response.jsonPath().getLong("id");

        assertTrue(id > 0);
    }

    @AfterEach
    @Step("Удалить тестового курьера")
    public void tearDown() {

        if (courierId > 0) {
            courierClient.delete(courierId);
        }
    }
}