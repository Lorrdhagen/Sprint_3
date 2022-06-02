package ru.practicum.scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.scooter.api.CourierApi;
import ru.practicum.scooter.api.model.Courier;
import ru.practicum.scooter.api.model.CourierCredentials;
import ru.practicum.scooter.api.model.CreateCourierResponse;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourierCreationTest {
    private int courierId;
    private static Courier courier;
    private static CourierApi courierApi;

    @Before
    public void init() {
        courier = Courier.getRandomCourier();
        courierApi = new CourierApi();
    }

    @After
    public void clear() {
        if (courierId != 0) {
            courierApi.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Создание курьера с корректными данными, проверка статус-кода и id курьера")
    public void courierCreationWithValidData() {
        Response responseCreate = courierApi.createCourier(courier);

        assertEquals(SC_CREATED, responseCreate.statusCode());
        CreateCourierResponse createCourierResponse = responseCreate.as(CreateCourierResponse.class);
        assertTrue(createCourierResponse.ok);

        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLogin = courierApi.loginCourier(courierCredentials);
        assertEquals(SC_OK, responseLogin.statusCode());
        courierId = responseLogin.body().jsonPath().getInt("id");
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Создание курьера без логина, проверка статус-кода и сообщения об ошибке")
    public void courierCreationWithoutLogin() {
        courier.setLogin("");
        Response responseCreate = courierApi.createCourier(courier);

        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        CreateCourierResponse createCourierResponse = responseCreate.as(CreateCourierResponse.class);
        assertEquals(createCourierResponse.getBadRequestMessage(), createCourierResponse.message);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Создание курьера без пароля, проверка статус-кода и сообщения об ошибке")
    public void courierCreationWithoutPassword() {
        courier.setPassword("");
        Response responseCreate = courierApi.createCourier(courier);

        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        CreateCourierResponse createCourierResponse = responseCreate.as(CreateCourierResponse.class);
        assertEquals(createCourierResponse.getBadRequestMessage(), createCourierResponse.message);
    }

    @Test
    @DisplayName("Создане курьера с существующим логином")
    @Description("Создание курьера с существующим логином, проверка статус-кода и сообщения об ошибке")
    public void courierCreationWithExistingLogin() {
        courierApi.createCourier(courier);
        Response responseExistingLogin = courierApi.createCourier(courier);

        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLogin = courierApi.loginCourier(courierCredentials);
        courierId = responseLogin.body().jsonPath().getInt("id");

        assertEquals(SC_CONFLICT, responseExistingLogin.statusCode());
        CreateCourierResponse createCourierResponse = responseExistingLogin.as(CreateCourierResponse.class);
        assertEquals(createCourierResponse.getConflictMessage(), createCourierResponse.message);
    }

}
