package com.zevoryn.inventory.product.application.command;

import java.math.BigDecimal;

public record CreateProductCommand(
        String sku,
        String name,
        String description,
        BigDecimal unitPrice
) {
}