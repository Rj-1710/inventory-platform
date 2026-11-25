package com.zevoryn.inventory.product.infrastructure.persistence;

import com.zevoryn.inventory.product.domain.Product;
import com.zevoryn.inventory.product.domain.ProductRepository;
import com.zevoryn.inventory.product.domain.exception.DuplicateSkuException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> productsBySku = new ConcurrentHashMap<>();

    @Override
    public Product save(Product product) {
        Product existingProduct = productsBySku.putIfAbsent(
                product.getSku(),
                product
        );

        if (existingProduct != null) {
            throw new DuplicateSkuException(product.getSku());
        }

        return product;
    }
}