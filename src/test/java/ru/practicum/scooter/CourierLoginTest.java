package ru.practicum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.practicum.scooter.api.CourierApi;
import ru.practicum.scooter.api.model.Courier;
import ru.practicum.scooter.api.model.CourierCredentials;
import ru.practicum.scooter.api.model.LoginCourierResponse;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {
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
    @DisplayName("Логин курьера")
    @Description("Логин курьера с корректными учетными данными, проверка статус-кода и id курьера")
    public void courierLoginWithRightCredentials() {
        courierApi.createCourier(courier);

        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLogin = courierApi.loginCourier(courierCredentials);
        assertEquals(SC_OK, responseLogin.statusCode());
        LoginCourierResponse loginCourierResponse = responseLogin.as(LoginCourierResponse.class);
        courierId = loginCourierResponse.getId();
        MatcherAssert.assertThat(courierId, notNullValue());
    }

    @Test
    @DisplayName("Логин курьера без логина")
    @Description("Логин курьера без логина, проверка статус-кода и сообщения об ошибке")
    public void courierLoginWithoutLogin() {
        courier.setLogin("");

        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLogin = courierApi.loginCourier(courierCredentials);
        assertEquals(SC_BAD_REQUEST, responseLogin.statusCode());
        LoginCourierResponse loginCourierResponse = responseLogin.as(LoginCourierResponse.class);
        assertEquals(loginCourierResponse.getBadRequestMessage(), loginCourierResponse.message);
    }

    @Test
    @DisplayName("Логин курьера без пароля")
    @Description("Логин курьера без пароля, проверка статус-кода и сообщения об ошибке")
    public void courierLoginWithoutPassword() {
        courier.setPassword("");

        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLogin = courierApi.loginCourier(courierCredentials);
        assertEquals(SC_BAD_REQUEST, responseLogin.statusCode());
        LoginCourierResponse loginCourierResponse = responseLogin.as(LoginCourierResponse.class);
        assertEquals(loginCourierResponse.getBadRequestMessage(), loginCourierResponse.message);
    }

    @Test
    @DisplayName("Логин курьера с некорректными учетными данными")
    @Description("Логин курьера с некорректными учетными данными, проверка статус-кода и сообщения об ошибке")
    public void courierLoginWithWrongCredentials() {
        CourierCredentials courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response responseLogin = courierApi.loginCourier(courierCredentials);
        assertEquals(SC_NOT_FOUND, responseLogin.statusCode());
        LoginCourierResponse loginCourierResponse = responseLogin.as(LoginCourierResponse.class);
        assertEquals(loginCourierResponse.getNotFoundMessage(), loginCourierResponse.message);
    }
}
