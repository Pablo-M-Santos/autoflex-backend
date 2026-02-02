package com.autoflex.productrawmaterial;

import com.autoflex.product.ProductEntity;
import com.autoflex.product.ProductRepository;
import com.autoflex.rawmaterial.RawMaterialEntity;
import com.autoflex.rawmaterial.RawMaterialRepository;
import com.autoflex.shared.exception.BusinessException;
import com.autoflex.shared.exception.NotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProductRawMaterialService {

    private final ProductRawMaterialRepository repository;
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductRawMaterialService(ProductRawMaterialRepository repository,
                                     ProductRepository productRepository,
                                     RawMaterialRepository rawMaterialRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @Transactional
    public void addRawMaterialToProduct(Long productId,
                                        Long rawMaterialId,
                                        Integer requiredQuantity) {

        if (requiredQuantity == null || requiredQuantity <= 0) {
            throw new BusinessException("Required quantity must be greater than zero");
        }

        ProductEntity product = productRepository.findById(productId);
        if (product == null) {
            throw new NotFoundException("Product not found");
        }

        RawMaterialEntity rawMaterial = rawMaterialRepository.findById(rawMaterialId);
        if (rawMaterial == null) {
            throw new NotFoundException("Raw material not found");
        }

        boolean exists = repository.find(
                "product = ?1 and rawMaterial = ?2",
                product,
                rawMaterial
        ).firstResultOptional().isPresent();

        if (exists) {
            throw new BusinessException("Raw material already associated with product");
        }

        ProductRawMaterialEntity entity = new ProductRawMaterialEntity();
        entity.setProduct(product);
        entity.setRawMaterial(rawMaterial);
        entity.setRequiredQuantity(requiredQuantity);

        repository.persist(entity);
    }
}
