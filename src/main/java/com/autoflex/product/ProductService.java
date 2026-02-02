package com.autoflex.product;

import com.autoflex.product.dto.ProductRequest;
import com.autoflex.product.dto.ProductResponse;
import com.autoflex.shared.exception.NotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductService {

    private final ProductRepository repository;
    

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductResponse> findAll() {
        return repository.listAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse findById(Long id) {
        ProductEntity entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Product not found");
        }
        return toResponse(entity);
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        ProductEntity entity = new ProductEntity();
        entity.setName(request.name);
        entity.setPrice(request.price);

        repository.persist(entity);
        return toResponse(entity);
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        ProductEntity entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Product not found");
        }

        entity.setName(request.name);
        entity.setPrice(request.price);

        return toResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Product not found");
        }
    }

    private ProductResponse toResponse(ProductEntity entity) {
        ProductResponse response = new ProductResponse();
        response.id = entity.getId();
        response.name = entity.getName();
        response.price = entity.getPrice();
        return response;
    }
}
