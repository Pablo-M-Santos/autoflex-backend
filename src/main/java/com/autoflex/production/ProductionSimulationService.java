package com.autoflex.production;

import com.autoflex.product.ProductEntity;
import com.autoflex.product.ProductRepository;
import com.autoflex.productrawmaterial.ProductRawMaterialEntity;
import com.autoflex.productrawmaterial.ProductRawMaterialRepository;
import com.autoflex.rawmaterial.RawMaterialEntity;
import com.autoflex.rawmaterial.RawMaterialRepository;
import com.autoflex.production.dto.ProductionResultResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

@ApplicationScoped
public class ProductionSimulationService {

    @Inject
    ProductRepository productRepository;

    @Inject
    ProductRawMaterialRepository productRawMaterialRepository;

    @Inject
    RawMaterialRepository rawMaterialRepository;

    public List<ProductionResultResponse> simulate() {


        Map<Long, Integer> stock = new HashMap<>();
        for (RawMaterialEntity rm : rawMaterialRepository.listAll()) {
            stock.put(rm.getId(), rm.getStockQuantity());
        }


        List<ProductEntity> products =
                productRepository.find("order by price desc").list();

        List<ProductionResultResponse> result =   new ArrayList<>();


        for (ProductEntity product : products) {

            List<ProductRawMaterialEntity> relations =
                    productRawMaterialRepository.find("product.id", product.getId()).list();

            int maxProduction = Integer.MAX_VALUE;

            for (ProductRawMaterialEntity prm : relations) {
                Long rawMaterialId = prm.getRawMaterial().getId();
                int required = prm.getRequiredQuantity();
                int available = stock.getOrDefault(rawMaterialId, 0);

                int possible = available / required;
                maxProduction = Math.min(maxProduction, possible);
            }

            if (maxProduction > 0 && maxProduction != Integer.MAX_VALUE) {


                for (ProductRawMaterialEntity prm : relations) {
                    Long rawMaterialId = prm.getRawMaterial().getId();
                    int required = prm.getRequiredQuantity();

                    stock.put(
                            rawMaterialId,
                            stock.get(rawMaterialId) - (required * maxProduction)
                    );
                }

                BigDecimal totalValue =
                        product.getPrice().multiply(BigDecimal.valueOf(maxProduction));

                result.add(new ProductionResultResponse(
                        product.getId(),
                        product.getName(),
                        maxProduction,
                        product.getPrice(),
                        totalValue
                ));
            }
        }

        return result;
    }
}
