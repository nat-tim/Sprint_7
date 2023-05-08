import ObjectApi.CourierForDel;
import StepApi.CourierApi;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.notNullValue;

public class TestLoginCourier {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        //создаем пользователя, чтоб залогиниться
        File json = new File("src/test/resources/newCourier.json");
        CourierApi.createCourier(json)
                .then().assertThat().statusCode(201);
    }

    @Test
    public void loginCourier(){
        File json = new File("src/test/resources/courier.json");
        //курьер может авторизоваться; успешный запрос возвращает id.
        CourierApi.loginCourier(json)
                .then().assertThat().statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());

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
