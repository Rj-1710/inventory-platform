package com.zevoryn.inventory.product.api;

import com.zevoryn.inventory.product.api.dto.CreateProductRequest;
import com.zevoryn.inventory.product.api.dto.ProductResponse;
import com.zevoryn.inventory.product.application.ProductService;
import com.zevoryn.inventory.product.application.command.CreateProductCommand;
import com.zevoryn.inventory.product.domain.Product;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public List<ProductResponse> listProducts() {
        return productService.listProducts()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }
}