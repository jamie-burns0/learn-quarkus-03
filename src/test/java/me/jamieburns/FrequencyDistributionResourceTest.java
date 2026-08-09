package me.jamieburns;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.matchesPattern;
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
            .body(
                matchesPattern("^\\[FrequencyCount\\[n=\\d+, f=\\d+](, FrequencyCount\\[n=\\d+, f=\\d+])*]$")
            );
    }

    @Test
    void testFdEndpointWithFixedList() {
        FrequencyDistributionService service = new FrequencyDistributionService();
        List<Integer> input = List.of(3, 1, 2, 1, 3, 3, 4);
        Map<Integer, Integer> expected = Map.of(
                1, 2,
                2, 1,
                3, 3,
                4, 1
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
        assertEquals(List.of(
            new FrequencyDistributionService.FrequencyCount(3, 3),
            new FrequencyDistributionService.FrequencyCount(1, 2),
            new FrequencyDistributionService.FrequencyCount(2, 1),
            new FrequencyDistributionService.FrequencyCount(4, 1)
        ), response.getEntity());
    }

    @Test
    void testSortedFdEndpointWithFixedList() {
        FrequencyDistributionService service = new FrequencyDistributionService();
        List<Integer> input = List.of(3, 1, 2, 1, 1, 3, 3, 4, 4);
        List<FrequencyDistributionService.FrequencyCount> expected = List.of(
            new FrequencyDistributionService.FrequencyCount(1, 3),
            new FrequencyDistributionService.FrequencyCount(3, 3),
            new FrequencyDistributionService.FrequencyCount(4, 2),
            new FrequencyDistributionService.FrequencyCount(2, 1)
        );

        assertEquals(expected, service.toFrequencyDistributionSortedByF(input));
    }
}