package com.autoflex.rawmaterial;

import com.autoflex.rawmaterial.dto.RawMaterialRequest;
import com.autoflex.rawmaterial.dto.RawMaterialResponse;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/raw-materials")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RawMaterialResource {

    private final RawMaterialService service;

    public RawMaterialResource(RawMaterialService service) {
        this.service = service;
    }

    @GET
    public List<RawMaterialResponse> findAll() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public RawMaterialResponse findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public RawMaterialResponse create(@Valid RawMaterialRequest request) {
        return service.create(request);
    }

    @PUT
    @Path("/{id}")
    public RawMaterialResponse update(@PathParam("id") Long id,
                                      @Valid RawMaterialRequest request) {
        return service.update(id, request);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }
}
