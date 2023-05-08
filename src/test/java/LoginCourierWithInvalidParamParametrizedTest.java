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
public class LoginCourierWithInvalidParamParametrizedTest {
    private final String login;
    private final String password;

    public LoginCourierWithInvalidParamParametrizedTest(String login, String password) {
        this.login = login;
        this.password = password;
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
        File json = new File("src/test/resources/newCourier.json");
        CourierApi.createCourier(json)
                .then().assertThat().statusCode(201);
    }

    @Test
    public void loginCourierWithInvalidParam(){
        Courier courier = new Courier(login, password);
        //система вернёт ошибку, если неправильно указать логин или пароль; если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
        CourierApi.loginCourier(courier)
                .then().assertThat().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));

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
