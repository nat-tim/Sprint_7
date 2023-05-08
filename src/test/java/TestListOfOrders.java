import StepApi.OrderApi;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

public class TestListOfOrders {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void takingListOfOrders(){
        //в тело ответа возвращается список заказов.
        OrderApi.checkListOfOrders();

    }


}
