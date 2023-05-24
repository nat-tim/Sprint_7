package test_api;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import object_api.Courier;
import object_api.CourierForDel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import step_api.CourierApi;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class TestLoginCourier {

    final static String login = "tatuka100";
    final static String password = "1234567";
    final static String firstName = "aske";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        //создаем пользователя, чтоб залогиниться
        Courier courier = new Courier(login, password, firstName);
        CourierApi.createCourier(courier)
                .then().assertThat().statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Check login courier")
    @Description("This is test which checks login courier and return courier's id")
    public void loginCourier() {
        Courier courier = new Courier(login, password);
        //курьер может авторизоваться; успешный запрос возвращает id.
        CourierApi.loginCourier(courier)
                .then().assertThat().statusCode(SC_OK)
                .and()
                .assertThat().body("id", notNullValue());

    }


    @After
    public void cleanUp() {
        Courier courier = new Courier(login, password);
        //delete created courier
        Response response = CourierApi.loginCourier(courier);
        response.then().assertThat().statusCode(SC_OK);
        CourierForDel courierDel = response.body().as(CourierForDel.class);

        CourierApi.deleteCourier(courierDel.getId());

    }
}
