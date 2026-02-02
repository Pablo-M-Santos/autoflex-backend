package com.autoflex.productrawmaterial;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ProductRawMaterialRepository
        implements PanacheRepository<ProductRawMaterialEntity> {

    public List<ProductRawMaterialEntity> findByProductId(Long productId) {
        return list("product.id", productId);
    }
}
