package com.zevoryn.inventory.product.infrastructure.persistence;

import com.zevoryn.inventory.product.domain.Product;
import com.zevoryn.inventory.product.domain.ProductRepository;
import com.zevoryn.inventory.product.domain.exception.DuplicateSkuException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;

import java.util.List;

@Repository
public class MySqlProductRepository implements ProductRepository {

    private final SpringDataProductRepository springDataProductRepository;

    public MySqlProductRepository(
            SpringDataProductRepository springDataProductRepository
    ) {
        this.springDataProductRepository = springDataProductRepository;
    }

    @Override
    public Product save(Product product) {
        try {
            return springDataProductRepository.saveAndFlush(product);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateSkuException(product.getSku());
        }
    }

    @Override
    public List<Product> findAll() {
        return springDataProductRepository.findAll(
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
    }
}