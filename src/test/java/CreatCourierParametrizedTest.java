import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreatCourierParametrizedTest {
    private final String login;
    private final String password;
    private final String firstName;

    public CreatCourierParametrizedTest(String login, String password,String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"tatuka100500", "", "aske"},
                {"", "123456", "aske"},
        };
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void createNewCourierWithoutOneParam(){
        //чтобы создать курьера, нужно передать в ручку все обязательные поля;если одного из полей нет, запрос возвращает ошибку;
        Courier courier = new Courier(login, password, firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }




}
