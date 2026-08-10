package me.jamieburns;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import me.jamieburns.FrequencyDistributionService.Frequency;
import me.jamieburns.FrequencyDistributionResource.FrequencyListResponse;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
class FrequencyDistributionResourceTest {
    @Test
    void testFdEndpoint() {
        given()
            .when().get("/fd")
            .then()
            .statusCode(200)
            .body("frequencies.size()", greaterThan(0))
            .body("frequencies[0]", hasKey("n"))
            .body("frequencies[0]", hasKey("f"));
    }

    @Test
    void testFdEndpointWithFixedList() {
        FrequencyDistributionService service = new FrequencyDistributionService();
        List<Integer> input = List.of(3, 1, 2, 1, 3, 3, 4);
        List<Frequency> expected = List.of(
                new Frequency(1, 2),
                new Frequency(2, 1),
                new Frequency(3, 3),
                new Frequency(4, 1)
        );

        assertEquals(expected, service.toFrequencyDistributionSortedByN(input));
    }

    @Test
    void testFdEndpointWithMockRandomService() {
        OneHundredRandomIntegersService mockRandomService = mock(OneHundredRandomIntegersService.class);
        when(mockRandomService.createRandomIntegersList()).thenReturn(List.of(3, 1, 2, 1, 3, 3, 4));

        FrequencyDistributionResource resource = new FrequencyDistributionResource(
                new FrequencyDistributionService(),
                mockRandomService
        );

        Response response = resource.fd();

        assertEquals(200, response.getStatus());
        assertEquals(new FrequencyListResponse(List.of(
            new Frequency(1, 2),
            new Frequency(2, 1),
            new Frequency(3, 3),
            new Frequency(4, 1)
        )), response.getEntity());
    }

    @Test
    void testSortedFdEndpointWithFixedList() {
        FrequencyDistributionService service = new FrequencyDistributionService();
        List<Integer> input = List.of(3, 1, 2, 1, 1, 3, 3, 4, 4);
        List<Frequency> expected = List.of(
            new Frequency(1, 3),
            new Frequency(3, 3),
            new Frequency(4, 2),
            new Frequency(2, 1)
        );

        assertEquals(expected, service.toFrequencyDistributionSortedByF(input));
    }
}