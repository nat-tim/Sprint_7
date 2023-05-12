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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import step_api.CourierApi;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class LoginCourierWithInvalidParamParametrizedTest {
    private final String loginParam;
    private final String passwordParam;
    final static String login = "tatuka100500";
    final static String password = "1234567";
    final static String firstName = "aske";

    public LoginCourierWithInvalidParamParametrizedTest(String loginParam, String passwordParam) {
        this.loginParam = loginParam;
        this.passwordParam = passwordParam;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"tatuka100500", "123"},
                {"tatuka", "123456"},

        };
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        //создаем пользователя, чтоб залогиниться
        Courier courier = new Courier(login, password, firstName);
        CourierApi.createCourier(courier)
                .then().assertThat().statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Check no way to login courier with invalid param")
    @Description("This is test which checks no way to login courier with invalid param")
    public void loginCourierWithInvalidParam() {
        Courier courier = new Courier(loginParam, passwordParam);
        //система вернёт ошибку, если неправильно указать логин или пароль; если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
        CourierApi.loginCourier(courier)
                .then().assertThat().statusCode(SC_NOT_FOUND)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));

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
