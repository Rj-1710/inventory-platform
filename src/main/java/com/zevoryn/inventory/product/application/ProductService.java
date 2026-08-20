package com.zevoryn.inventory.product.application;

import com.zevoryn.inventory.product.application.command.CreateProductCommand;
import com.zevoryn.inventory.product.domain.Product;
import com.zevoryn.inventory.product.domain.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(CreateProductCommand command) {
        Product product = new Product(
                command.sku(),
                command.name(),
                command.description(),
                command.unitPrice()
        );

        return productRepository.save(product);
    }
}