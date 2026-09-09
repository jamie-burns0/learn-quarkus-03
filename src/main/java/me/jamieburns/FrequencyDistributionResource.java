package me.jamieburns;

import java.util.List;

import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/fd")
public class FrequencyDistributionResource {

    public record FrequencyListResponse(List<Frequency> frequencies) {}

    private FrequencyDistributionService fdService;
    private OneHundredRandomIntegersService randomService;

    public FrequencyDistributionResource(@Named("FDS3") FrequencyDistributionService fdService, OneHundredRandomIntegersService randomService) {
        this.fdService = fdService;
        this.randomService = randomService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fd() {
        return Response
            .ok()
            .entity(new FrequencyListResponse(
                fdService.toFrequencyDistributionSortedByN(randomService.createRandomIntegersList())
            ))
            .build();
    }

    @GET
    @Path("/sorted")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fdSorted() {
        return Response
            .ok()
            .entity(new FrequencyListResponse(
                fdService.toFrequencyDistributionSortedByF(randomService.createRandomIntegersList())
            ))
            .build();
    }
}
