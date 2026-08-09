package me.jamieburns;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/fd")
public class FrequencyDistributionResource {

    private FrequencyDistributionService fdService;
    private OneHundredRandomIntegersService randomService;

    public FrequencyDistributionResource(FrequencyDistributionService fdService, OneHundredRandomIntegersService randomService) {
        this.fdService = fdService;
        this.randomService = randomService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fd() {
        var r = Response
                .ok()
                .entity(fdService.toFrequencyDistributionSortedByF(randomService.createRandomIntegersList()))
                .build();
        return r;
    }

    @GET
    @Path("/sorted")
    @Produces(MediaType.APPLICATION_JSON)
    public Response fdSorted() {
        var r = Response
                .ok()
                .entity(fdService.toFrequencyDistributionSortedByF(randomService.createRandomIntegersList()))
                .build();
        return r;
    }
}
