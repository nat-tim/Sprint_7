package step_api;

import io.restassured.response.Response;
import object_api.Courier;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class CourierApi {

    final static String createApi = "/api/v1/courier";
    final static String loginApi = "/api/v1/courier/login";
    final static String delApi = "/api/v1/courier/";


    public static Response createCourier(Courier courier) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(createApi);
        return response;

    }


    public static Response loginCourier(Courier courier) {

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(loginApi)
                .then().extract().response();
        return response;
    }

    public static void deleteCourier(String id) {

        given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete(delApi + id)
                .then().assertThat().statusCode(SC_OK);
    }
}
