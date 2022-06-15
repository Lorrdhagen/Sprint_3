package ru.practicum.scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum.scooter.api.model.Order;

import static io.restassured.RestAssured.given;

public class OrderApi extends BaseApiSpec {

    @Step("Создать заказ")
    public Response createOrder(Order order) {
        return given()
                .spec(getInitSpec())
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Получить заказ по его номеру")
    public int getOrderByTrack(int track) {
        return given()
                .spec(getInitSpec())
                .param("t", track)
                .when()
                .get("/api/v1/orders/track")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("order.id");
    }

    @Step("Принять заказ")
    public boolean acceptOrder(int orderId, int courier) {
        return given()
                .spec(getInitSpec())
                .param("courierId", courier)
                .when()
                .put("/api/v1/orders/accept/" + orderId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

    @Step("Получить список заказов")
    public Response getOrderList() {
        return given()
                .spec(getInitSpec())
                .when()
                .get("/api/v1/orders");
    }
}
