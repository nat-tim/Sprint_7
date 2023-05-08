package StepApi;

import ObjectApi.Courier;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CourierApi {

    public static Response createCourier(File json){
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
        return response;

    }

    public static Response createCourier(Courier courier){
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
        return response;

    }

    public static Response loginCourier(File json){

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().extract().response();
        return response;
    }

    public static Response loginCourier(Courier courier){

        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then().extract().response();
        return response;
    }

    public static void deleteCourier(String id){

        given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete("/api/v1/courier/" + id)
                .then().assertThat().statusCode(200);
    }
}
