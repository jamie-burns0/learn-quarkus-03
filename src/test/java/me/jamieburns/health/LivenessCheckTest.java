package me.jamieburns.health;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest 
public class LivenessCheckTest {

    @Test
    void testLivenessEndpoint() {
        given()
            .when().get("/q/health/live")
            .then()
            .statusCode(200)
            .body(
                "checks.find { check -> check.name == 'liveness-check' }.status",
                is("UP"));
    }    
}
