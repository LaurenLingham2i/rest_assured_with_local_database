import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestAssuredTests {

    @BeforeClass
    public static void createRequestSpecification() {
        RestAssured.baseURI = "http://localhost:3002/people";
    }

    @Test
    public void a_getAllData() {
        given()
            .log().all()
            .when()
                .get()
            .then()
                .log().body()
                    .statusCode(200);
    }

    @Test
    public void b_getOneItem() {
        given()
            .log().all()
            .when()
                .get("/14")
            .then()
                .log().body()
                    .statusCode(200)
                    .body("fullName", equalTo("Hannah Anderson"))
                    .body("email", equalTo("hannah.anderson@example.com"))
                    .body("job", equalTo("UX/UI Designer"))
                    .body("dob", equalTo("09/23/1989"));
    }

    @Test
    public void c_createPerson() {
        JSONObject requestBody = new JSONObject()
            .appendField("id", "21")
            .appendField("fullName", "Keanu Reeves")
            .appendField("email", "keanu.reeves@unknown.com")
            .appendField("job", "Cowboy")
            .appendField("dob", "01/01/2023");

        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
                .post()
            .then()
                .statusCode(201);
    }

    @Test
    public void c_createPerson2() {
        // Define request body with JSON string to ensure correct order of fields
        String requestBody = "{"
            + "\"id\": \"21\","
            + "\"fullName\": \"Keanu Reeves\","
            + "\"email\": \"keanu.reeves@unknown.com\","
            + "\"job\": \"Cowboy\","
            + "\"dob\": \"01/01/2023\""
            + "}";

        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
                .post()
            .then()
                .statusCode(201);
    }

    @Test
    public void updatePersonName() {
        JSONObject updateRequestBody = new JSONObject()
            .appendField("fullName", "Jesse James");

        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(updateRequestBody)
            .when()
                .patch("/21")
            .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void updatePerson() {
        JSONObject updateRequestBody = new JSONObject()
                .appendField("id", "21")
                .appendField("fullName", "Jesse James")
                .appendField("email", "jesse.james@unknown.com")
                .appendField("job", "Cowboy")
                .appendField("dob", "01/01/2023");

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(updateRequestBody)
                .when()
                    .put("/21")
                .then()
                    .log().all()
                    .statusCode(200);
    }

    @Test
    public void d_deleteOne() {
        given()
                .log().all()
            .when()
                .delete("/21")
            .then()
                .log().body()
                .statusCode(200);
    }
}
