package com.zevoryn.inventory.product.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductRequest(

        @NotBlank(message = "SKU is required.")
        @Pattern(
                regexp = "^[A-Z0-9-]{3,30}$",
                message = "SKU must contain 3 to 30 uppercase letters, digits, or hyphens."
        )
        String sku,

        @NotBlank(message = "Product name is required.")
        @Size(
                min = 2,
                max = 150,
                message = "Product name must contain between 2 and 150 characters."
        )
        String name,

        @Size(
                max = 1000,
                message = "Product description cannot exceed 1000 characters."
        )
        String description,

        @NotNull(message = "Unit price is required.")
        @DecimalMin(
                value = "0.01",
                message = "Unit price must be greater than zero."
        )
        BigDecimal unitPrice
) {
}