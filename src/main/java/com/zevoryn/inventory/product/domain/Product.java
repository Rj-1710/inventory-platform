package com.zevoryn.inventory.product.domain;

import com.zevoryn.inventory.product.domain.exception.InvalidProductException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.regex.Pattern;

public class Product {

    private static final Pattern SKU_PATTERN =
            Pattern.compile("^[A-Z0-9-]{3,30}$");

    private final UUID id;
    private final String sku;
    private final String name;
    private final String description;
    private final BigDecimal unitPrice;
    private ProductStatus status;
    private final Instant createdAt;

    public Product(String sku, String name, String description, BigDecimal unitPrice) {
        validateSku(sku);
        validateName(name);
        validateDescription(description);
        validateUnitPrice(unitPrice);

        this.id = UUID.randomUUID();
        this.sku = sku.trim();
        this.name = name.trim();
        this.description = description == null || description.isBlank()
                ? null
                : description.trim();
        this.unitPrice = unitPrice;
        this.status = ProductStatus.ACTIVE;
        this.createdAt = Instant.now();
    }

    private void validateSku(String sku) {
        if (sku == null || !SKU_PATTERN.matcher(sku.trim()).matches()) {
            throw new InvalidProductException(
                    "SKU must contain 3 to 30 uppercase letters, digits, or hyphens."
            );
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidProductException("Product name is required.");
        }

        int nameLength = name.trim().length();

        if (nameLength < 2 || nameLength > 150) {
            throw new InvalidProductException(
                    "Product name must contain between 2 and 150 characters."
            );
        }
    }

    private void validateDescription(String description) {
        if (description != null && description.trim().length() > 1000) {
            throw new InvalidProductException(
                    "Product description cannot exceed 1000 characters."
            );
        }
    }

    private void validateUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidProductException(
                    "Unit price must be greater than zero."
            );
        }
    }

    public UUID getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}