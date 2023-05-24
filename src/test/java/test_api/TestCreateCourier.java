package test_api;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import object_api.Courier;
import object_api.CourierForDel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import step_api.CourierApi;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class TestCreateCourier {
    final static String login = "tatuka1001";
    final static String password = "1234567";
    final static String firstName = "aske";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check create new courier")
    @Description("This is test which checks creation new courier")
    public void createNewCourier() {
        Courier courier = new Courier(login, password, firstName);
        //курьера можно создать; запрос возвращает правильный код ответа;успешный запрос возвращает ok: true;
        Response response = CourierApi.createCourier(courier);
        checkCreateNewCourier(response);

    }

    @Test
    @DisplayName("Check not to be two similar courier")
    @Description("This is test which checks creation new courier and no way to create similar courier")
    public void notBeTwoTwinsCourier() {
        Courier courier = new Courier(login, password, firstName);
        //нельзя создать двух одинаковых курьеров; если создать пользователя с логином, который уже есть, возвращается ошибка.
        Response response = CourierApi.createCourier(courier);
        checkCreateNewCourier(response);
        Response response1 = CourierApi.createCourier(courier);
        notCreateSimilarCourier(response1);
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


    // метод для шага "Создать нового курьера":
    @Step("check create new courier")
    public void checkCreateNewCourier(Response response) {
        response.then().assertThat().statusCode(SC_CREATED)
                .and()
                .assertThat().body("ok", equalTo(true));
    }

    // метод для шага "Нельзя создать такого же крьера":
    @Step("no way to create similar courier")
    public void notCreateSimilarCourier(Response response) {
        response.then().assertThat().statusCode(SC_CONFLICT)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
