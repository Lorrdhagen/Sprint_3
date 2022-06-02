package ru.practicum.scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.practicum.scooter.api.OrderApi;
import ru.practicum.scooter.api.model.Order;
import ru.practicum.scooter.api.model.CreateOrderResponse;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTest {

    private int orderId;
    private static OrderApi orderApi;
    private static Order order;
    private int track;
    private String[] color;

    public OrderCreationTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Тестовые данные цвета самоката")
    public static Object[][] getTestData() {
        return new Object[][]{
                {new String[]{"BLACK", "GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{}}
        };
    }

    @Before
    public void init() {
        orderApi = new OrderApi();
        order = Order.getRandomOrder();
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Создание заказа с корректными данными, проветка статус-кода и тела ответа")
    public void orderCreation() {
        order.setColor(color);
        Response responseOrderCreate = orderApi.createOrder(order);

        assertEquals(SC_CREATED, responseOrderCreate.statusCode());
        CreateOrderResponse createOrderResponse = responseOrderCreate.as(CreateOrderResponse.class);
        track = createOrderResponse.getTrack();
        MatcherAssert.assertThat(track, notNullValue());
        orderId = orderApi.getOrderByTrack(track);
    }
}
