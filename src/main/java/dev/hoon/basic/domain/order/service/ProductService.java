package dev.hoon.basic.domain.order.service;

import dev.hoon.basic.domain.order.model.Product;
import dev.hoon.basic.domain.order.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(long id) {

        return productRepository.findById(id);
    }

}
