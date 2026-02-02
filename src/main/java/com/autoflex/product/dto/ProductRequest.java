package com.autoflex.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class ProductRequest {

    @NotBlank
    public String name;

    @NotNull
    @Positive
    public BigDecimal price;
}
