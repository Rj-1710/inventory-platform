package com.zevoryn.inventory.product.api.dto;

import com.zevoryn.inventory.product.domain.Product;
import com.zevoryn.inventory.product.domain.ProductStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String sku,
        String name,
        String description,
        BigDecimal unitPrice,
        ProductStatus status,
        Instant createdAt
) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getUnitPrice(),
                product.getStatus(),
                product.getCreatedAt()
        );
    }
}