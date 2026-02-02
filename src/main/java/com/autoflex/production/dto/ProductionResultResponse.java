package com.autoflex.production.dto;

import java.math.BigDecimal;

public class ProductionResultResponse {

    public Long productId;
    public String productName;
    public Integer quantityProduced;
    public BigDecimal unitPrice;
    public BigDecimal totalValue;

    public ProductionResultResponse(
            Long productId,
            String productName,
            Integer quantityProduced,
            BigDecimal unitPrice,
            BigDecimal totalValue
    ) {
        this.productId = productId;
        this.productName = productName;
        this.quantityProduced = quantityProduced;
        this.unitPrice = unitPrice;
        this.totalValue = totalValue;
    }
}
