import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestLoginCourier {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        //создаем пользователя, чтоб залогиниться
        File json = new File("src/test/resources/newCourier.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(201);
    }

    @Test
    public void loginCourier(){
        File json = new File("src/test/resources/courier.json");
        //курьер может авторизоваться; успешный запрос возвращает id.
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());

    }

    @Test
    public void loginCourierWithInvalidParam(){
        File json = new File("src/test/resources/courier.json");
        //курьер может авторизоваться; успешный запрос возвращает id.
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().assertThat().statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());

    }


    @After
    public void cleanUp() {
        //удаляем пользователя
        File json = new File("src/test/resources/courier.json");
        //узнаем id
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().extract().response();
        response.then().assertThat().statusCode(200);
        CourierForDel courier = response.body().as(CourierForDel.class);

        //удаляем
        given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete("/api/v1/courier/" + courier.getId())
                .then().assertThat().statusCode(200);


    }
}
