package com.zevoryn.inventory.product.infrastructure.persistence;

import com.zevoryn.inventory.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataProductRepository
        extends JpaRepository<Product, UUID> {
}