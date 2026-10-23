package me.jamieburns.health;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest 
public class ReadinessCheckTest {

    @Test
    void testReadinessEndpoint() {
        given()
            .when().get("/q/health/ready")
            .then()
            .statusCode(200)
            .body(
                "checks.find { check -> check.name == 'readiness-check' }.status",
                is("UP"));
    }
}
