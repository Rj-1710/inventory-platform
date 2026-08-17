package com.zevoryn.inventory.product.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import com.zevoryn.inventory.product.domain.exception.InvalidProductException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductTest {

    @Test
    void shouldCreateAnActiveProductWithGeneratedId() {
        Product product = new Product(
                "IPHONE-16",
                "iPhone 16",
                "Latest iPhone model",
                new BigDecimal("79999.99")
        );

        assertNotNull(product.getId());
        assertEquals("IPHONE-16", product.getSku());
        assertEquals(ProductStatus.ACTIVE, product.getStatus());
        assertEquals(new BigDecimal("79999.99"), product.getUnitPrice());
        assertNotNull(product.getCreatedAt());
    }

    @Test
    void shouldRejectSkuWithLowercaseLetters() {
        assertThrows(InvalidProductException.class, () ->
                new Product(
                        "iphone-16",
                        "iPhone 16",
                        null,
                        new BigDecimal("79999.99")
                )
        );
    }

    @Test
    void shouldRejectBlankProductName() {
        assertThrows(InvalidProductException.class, () ->
                new Product(
                        "IPHONE-16",
                        "   ",
                        null,
                        new BigDecimal("79999.99")
                )
        );
    }

    @Test
    void shouldRejectZeroPrice() {
        assertThrows(InvalidProductException.class, () ->
                new Product(
                        "IPHONE-16",
                        "iPhone 16",
                        null,
                        BigDecimal.ZERO
                )
        );
    }

    @Test
    void shouldRejectNegativePrice() {
        assertThrows(InvalidProductException.class, () ->
                new Product(
                        "IPHONE-16",
                        "iPhone 16",
                        null,
                        new BigDecimal("-1.00")
                )
        );
    }
}