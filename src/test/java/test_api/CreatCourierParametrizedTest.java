package test_api;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import object_api.Courier;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import step_api.CourierApi;


import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.Matchers.equalTo;


@RunWith(Parameterized.class)
public class CreatCourierParametrizedTest {
    private final String login;
    private final String password;
    private final String firstName;

    public CreatCourierParametrizedTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"tatuka100500", "", "aske"},
                {"", "123456", "aske"},
        };
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check no way to create new courier without one param")
    @Description("This is test which checks no way to create new courier without one param")
    public void createNewCourierWithoutOneParam() {
        //чтобы создать курьера, нужно передать в ручку все обязательные поля;если одного из полей нет, запрос возвращает ошибку;
        Courier courier = new Courier(login, password, firstName);
        Response response = CourierApi.createCourier(courier);
        response
                .then().assertThat().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }


}
