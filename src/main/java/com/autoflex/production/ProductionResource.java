package com.autoflex.production;

import com.autoflex.production.dto.ProductionResultResponse;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/production")
@Produces(MediaType.APPLICATION_JSON)
public class ProductionResource {

    @Inject
    ProductionSimulationService productionSimulationService;

    @GET
    @Path("/simulation")
    public List<ProductionResultResponse> simulateProduction() {
        return productionSimulationService.simulate();
    }
}
