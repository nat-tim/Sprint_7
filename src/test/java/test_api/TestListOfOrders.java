package test_api;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import io.qameta.allure.junit4.DisplayName;
import step_api.OrderApi;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

public class TestListOfOrders {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check getting list of orders")
    @Description("This is test which checks getting list of orders")
    @TmsLink("TestCase-112")
    @Issue("BUG-985")
    public void takingListOfOrders() {
        //в тело ответа возвращается список заказов.
        OrderApi.checkListOfOrders();

    }


}
