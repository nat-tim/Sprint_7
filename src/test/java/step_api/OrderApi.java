package step_api;

import object_api.Order;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class OrderApi {

    final static String ordersApi = "/api/v1/orders";

    public static void checkListOfOrders() {
        given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get(ordersApi)
                .then().assertThat().statusCode(SC_OK)
                .and()
                .assertThat().body("orders", notNullValue());
    }

    public static void createOrder(Order order) {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(ordersApi)
                .then().assertThat().statusCode(SC_CREATED)
                .and()
                .assertThat().body("track", notNullValue());
    }
}
