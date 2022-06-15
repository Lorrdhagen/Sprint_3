package ru.practicum.scooter.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.practicum.scooter.api.model.Courier;
import ru.practicum.scooter.api.model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierApi extends BaseApiSpec {

    @Step("Создать курьера")
    public Response createCourier(Courier courier) {
        return given()
                .spec(getInitSpec())
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Логин курьера")
    public Response loginCourier(CourierCredentials courierCredentials) {
        return given()
                .spec(getInitSpec())
                .body(courierCredentials)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удалить курьера")
    public Boolean deleteCourier(int courier) {
        return given()
                .spec(getInitSpec())
                .when()
                .delete("/api/v1/courier/" + courier)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }
}
