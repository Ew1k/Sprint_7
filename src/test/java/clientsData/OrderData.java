package clientsData;

import models.Order;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class OrderData {

    public static Stream<Arguments> getOrdersWithDifferentColors() {

        return Stream.of(
                Arguments.of(
                        "BLACK",
                        getOrder(Collections.singletonList("BLACK"))
                ),
                Arguments.of(
                        "GREY",
                        getOrder(Collections.singletonList("GREY"))
                ),
                Arguments.of(
                        "BLACK + GREY",
                        getOrder(List.of("BLACK", "GREY"))
                ),
                Arguments.of(
                        "без цвета",
                        getOrder(null)
                )
        );
    }

    private static Order getOrder(List<String> color) {

        return new Order(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                5,
                "2026-10-10",
                "Saske, come back to Konoha",
                color
        );
    }
}