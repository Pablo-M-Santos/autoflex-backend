package com.autoflex.rawmaterial;

import com.autoflex.rawmaterial.dto.RawMaterialRequest;
import com.autoflex.rawmaterial.dto.RawMaterialResponse;
import com.autoflex.shared.exception.NotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RawMaterialService {

    private final RawMaterialRepository repository;

    public RawMaterialService(RawMaterialRepository repository) {
        this.repository = repository;
    }

    public List<RawMaterialResponse> findAll() {
        return repository.listAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RawMaterialResponse findById(Long id) {
        RawMaterialEntity entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Raw material not found");
        }
        return toResponse(entity);
    }

    @Transactional
    public RawMaterialResponse create(RawMaterialRequest request) {
        RawMaterialEntity entity = new RawMaterialEntity();
        entity.setName(request.name);
        entity.setStockQuantity(request.stockQuantity);

        repository.persist(entity);
        return toResponse(entity);
    }

    @Transactional
    public RawMaterialResponse update(Long id, RawMaterialRequest request) {
        RawMaterialEntity entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Raw material not found");
        }

        entity.setName(request.name);
        entity.setStockQuantity(request.stockQuantity);

        return toResponse(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Raw material not found");
        }
    }

    private RawMaterialResponse toResponse(RawMaterialEntity entity) {
        RawMaterialResponse response = new RawMaterialResponse();
        response.id = entity.getId();
        response.name = entity.getName();
        response.stockQuantity = entity.getStockQuantity();
        return response;
    }
}
