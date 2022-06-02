package ru.practicum.scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;

import org.junit.Before;
import org.junit.Test;
import ru.practicum.scooter.api.OrderApi;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {

    private static OrderApi orderApi;

    @Before
    public void init() {
        orderApi = new OrderApi();
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Получение списка заказов, проверка статус-кода и тела ответа")
    public void getAllOrdersList() {
        Response responseGetOrderList = orderApi.getOrderList();
        assertEquals(SC_OK, responseGetOrderList.statusCode());
        MatcherAssert.assertThat((responseGetOrderList.body().jsonPath().getList("orders")), notNullValue());
    }
}
