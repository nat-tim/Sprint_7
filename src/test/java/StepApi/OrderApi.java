package StepApi;

import ObjectApi.Order;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrderApi {

    public static void checkListOfOrders(){
        given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/api/v1/orders")
                .then().assertThat().statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());
    }

    public static void createOrder(Order order){
        given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then().assertThat().statusCode(201)
                .and()
                .assertThat().body("track", notNullValue());
    }
}
