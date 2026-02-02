package com.autoflex.product;

import com.autoflex.product.dto.ProductRequest;
import com.autoflex.product.dto.ProductResponse;
import com.autoflex.productrawmaterial.ProductRawMaterialService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private final ProductService service;
    private final ProductRawMaterialService productRawMaterialService;

    public ProductResource(ProductService service,
                           ProductRawMaterialService productRawMaterialService) {
        this.service = service;
        this.productRawMaterialService = productRawMaterialService;
    }


    @GET
    public List<ProductResponse> findAll() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public ProductResponse findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public ProductResponse create(@Valid ProductRequest request) {
        return service.create(request);
    }

    @PUT
    @Path("/{id}")
    public ProductResponse update(@PathParam("id") Long id,
                                  @Valid ProductRequest request) {
        return service.update(id, request);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }

    @POST
    @Path("/{productId}/raw-materials/{rawMaterialId}")
    public void addRawMaterial(@PathParam("productId") Long productId,
                               @PathParam("rawMaterialId") Long rawMaterialId,
                               @QueryParam("quantity") Integer quantity) {
        productRawMaterialService.addRawMaterialToProduct(
                productId,
                rawMaterialId,
                quantity
        );
    }

}
