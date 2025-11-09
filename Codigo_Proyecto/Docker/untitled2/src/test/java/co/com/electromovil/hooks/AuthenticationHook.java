package co.com.electromovil.hooks;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationHook {

    private static final String BASE_URL = "http://localhost:8000";
    private static final String LOGIN_ENDPOINT = "/api/login";
    private static String authToken = null;

    @Before(value = "@requiresAuth", order = 1)
    public void authenticateViaApi() {
        if (authToken == null) {
            Map<String, String> credentials = new HashMap<>();
            credentials.put("email", "juanpoxc@gmail.com");
            credentials.put("password", "147258369po");

            Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post(BASE_URL + LOGIN_ENDPOINT);

            Assert.assertEquals(200, response.getStatusCode());
            
            // Extraer el token de la respuesta
            authToken = response.jsonPath().getString("access_token");
            Assert.assertNotNull("Token no debe ser null", authToken);
        }
    }

    @Before(order = 0)
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    public static String getAuthToken() {
        return authToken;
    }
}