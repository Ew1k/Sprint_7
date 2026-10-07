package clientsData;

import clients.CourierClient;
import models.Courier;
import models.CourierCreds;
import models.CourierCredsResponse;

import io.restassured.response.Response;

import static models.CourierCreds.credsFromCourier;

public class GetCourierId {

    private final CourierClient courierClient = new CourierClient();

    public long getCourierId(Courier courier) {
        Response loginResponse = courierClient.login(credsFromCourier(courier));

        return loginResponse.as(CourierCredsResponse.class).getId();
    }
}
