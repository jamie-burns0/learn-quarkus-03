package me.jamieburns.health;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest 
public class StartupCheckTest {

    @Test
    void testStartupEndpoint() {
        given()
            .when().get("/q/health/started")
            .then()
            .statusCode(200)
            .body(
                "checks.find { check -> check.name == 'startup-check' }.status",
                is("UP"));
    } 
}
