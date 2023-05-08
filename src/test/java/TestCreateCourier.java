import ObjectApi.CourierForDel;
import StepApi.CourierApi;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.equalTo;

public class TestCreateCourier {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createNewCourier(){
        File json = new File("src/test/resources/newCourier.json");
        //курьера можно создать; запрос возвращает правильный код ответа;успешный запрос возвращает ok: true;
        Response response = CourierApi.createCourier(json);
        response.then().assertThat().statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));

    }

    @Test
    public void notBeTwoTwinsCourier(){
        File json = new File("src/test/resources/newCourier.json");
        //нельзя создать двух одинаковых курьеров; если создать пользователя с логином, который уже есть, возвращается ошибка.
        Response response = CourierApi.createCourier(json);
        response.then().assertThat().statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
        Response response1 = CourierApi.createCourier(json);
        response1.then().assertThat().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void cleanUp() {
        File json = new File("src/test/resources/courier.json");
        //delete created courier
        Response response = CourierApi.loginCourier(json);
                response.then().assertThat().statusCode(200);
        CourierForDel courier = response.body().as(CourierForDel.class);

        CourierApi.deleteCourier(courier.getId());


    }
}
