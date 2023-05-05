import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
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
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));

    }

    @Test
    public void notBeTwoTwinsCourier(){
        File json = new File("src/test/resources/newCourier.json");
        //нельзя создать двух одинаковых курьеров; если создать пользователя с логином, который уже есть, возвращается ошибка.
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void cleanUp() {
        File json = new File("src/test/resources/courier.json");
        //delete created courier
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().extract().response();
                response.then().assertThat().statusCode(200);
        CourierForDel courier = response.body().as(CourierForDel.class);


        given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete("/api/v1/courier/" + courier.getId())
                .then().assertThat().statusCode(200);


    }
}
