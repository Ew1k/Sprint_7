package clientsData;

import models.Courier;
import models.CourierCreds;

import java.util.UUID;

public class CourierData {

    public static Courier getCourier() {
        return new Courier(
                "courier_" + UUID.randomUUID(),
                "12345",
                "uchixa"
        );
    }

    public static Courier getCourierWithoutLogin() {
        return new Courier(
                null,
                "12345",
                "uchixa"
        );
    }

    public static Courier getCourierWithoutPassword() {
        return new Courier(
                "courier_" + UUID.randomUUID(),
                null,
                "uchixa"
        );
    }

    public static Courier getCourierWithoutFirstName() {
        return new Courier(
                "courier_" + UUID.randomUUID(),
                "12345",
                null
        );
    }

    public static CourierCreds getCredentials(Courier courier) {
        return new CourierCreds(
                courier.getLogin(),
                courier.getPassword()
        );
    }
}