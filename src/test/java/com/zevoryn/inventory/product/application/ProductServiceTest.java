package com.zevoryn.inventory.product.application;

import com.zevoryn.inventory.product.application.command.CreateProductCommand;
import com.zevoryn.inventory.product.domain.Product;
import com.zevoryn.inventory.product.domain.ProductStatus;
import com.zevoryn.inventory.product.domain.exception.DuplicateSkuException;
import com.zevoryn.inventory.product.infrastructure.persistence.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(
                new InMemoryProductRepository()
        );
    }

    @Test
    void shouldCreateProduct() {
        CreateProductCommand command = new CreateProductCommand(
                "IPHONE-16",
                "iPhone 16",
                "Latest iPhone model",
                new BigDecimal("79999.99")
        );

        Product product = productService.createProduct(command);

        assertNotNull(product.getId());
        assertEquals("IPHONE-16", product.getSku());
        assertEquals(ProductStatus.ACTIVE, product.getStatus());
    }

    @Test
    void shouldRejectDuplicateSku() {
        CreateProductCommand command = new CreateProductCommand(
                "IPHONE-16",
                "iPhone 16",
                null,
                new BigDecimal("79999.99")
        );

        productService.createProduct(command);

        assertThrows(
                DuplicateSkuException.class,
                () -> productService.createProduct(command)
        );
    }
}