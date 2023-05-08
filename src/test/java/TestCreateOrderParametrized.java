import ObjectApi.Order;
import StepApi.OrderApi;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class TestCreateOrderParametrized {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final int metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;

    public TestCreateOrderParametrized(String firstName, String lastName, String address,
                                       int metroStation, String phone, int rentTime, String deliveryDate,
                                       String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06",
                        "Saske, come back to Konoha", Arrays.asList("BLACK")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06",
                        "Saske, come back to Konoha",  Arrays.asList("BLACK", "GREY")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06",
                        "Saske, come back to Konoha", Arrays.asList("GREY")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06",
                        "Saske, come back to Konoha", Arrays.asList()},

        };
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";

    }

    @Test
    public void createOrder(){
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate,
                comment, color);
        //система вернёт ошибку, если неправильно указать логин или пароль; если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
        OrderApi.createOrder(order);

    }

}
