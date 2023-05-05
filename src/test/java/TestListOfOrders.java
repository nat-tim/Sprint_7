import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class TestListOfOrders {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void takingListOfOrders(){
        //в тело ответа возвращается список заказов.
        given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/api/v1/orders")
                .then().assertThat().statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());

    }


}
