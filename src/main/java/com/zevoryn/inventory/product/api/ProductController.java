package com.zevoryn.inventory.product.api;

import com.zevoryn.inventory.product.api.dto.CreateProductRequest;
import com.zevoryn.inventory.product.api.dto.ProductResponse;
import com.zevoryn.inventory.product.application.ProductService;
import com.zevoryn.inventory.product.application.command.CreateProductCommand;
import com.zevoryn.inventory.product.domain.Product;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody CreateProductRequest request
    ) {
        Product product = productService.createProduct(
                new CreateProductCommand(
                        request.sku(),
                        request.name(),
                        request.description(),
                        request.unitPrice()
                )
        );

        return ResponseEntity
                .created(URI.create("/api/products/" + product.getId()))
                .body(ProductResponse.from(product));
    }
}