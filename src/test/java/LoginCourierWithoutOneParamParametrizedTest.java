import ObjectApi.Courier;
import ObjectApi.CourierForDel;
import StepApi.CourierApi;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class LoginCourierWithoutOneParamParametrizedTest {
    private final String login;
    private final String password;

    public LoginCourierWithoutOneParamParametrizedTest(String login, String password) {
        this.login = login;
        this.password = password;
    }
    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"tatuka100500", ""},
                {"", "123456"},

        };
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        //создаем пользователя, чтоб залогиниться
        File json = new File("src/test/resources/newCourier.json");
        CourierApi.createCourier(json)
                .then().assertThat().statusCode(201);
    }

    @Test
    public void loginCourierWithoutOneParam(){
        Courier courier = new Courier(login, password);
        //для авторизации нужно передать все обязательные поля; если какого-то поля нет, запрос возвращает ошибку;
        CourierApi.loginCourier(courier)
                .then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));

    }

    @After
    public void cleanUp() {
        //удаляем пользователя
        File json = new File("src/test/resources/courier.json");
        //узнаем id
        Response response = CourierApi.loginCourier(json);
        response.then().assertThat().statusCode(200);
        CourierForDel courier = response.body().as(CourierForDel.class);

        CourierApi.deleteCourier(courier.getId());

    }

}
