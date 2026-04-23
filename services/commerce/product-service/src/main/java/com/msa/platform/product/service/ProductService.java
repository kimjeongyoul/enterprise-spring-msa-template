package com.msa.platform.product.service;

import com.msa.platform.product.domain.Product;
import com.msa.platform.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void reduceStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        product.reduceStock(quantity);
        log.info("Stock reduced for product {}: remains {}", productId, product.getStock());
    }

    @Transactional
    public void restoreStock(Long productId, int quantity) {
        productRepository.findById(productId).ifPresent(p -> {
            p.increaseStock(quantity);
            log.info("Stock restored for product {}: now {}", productId, p.getStock());
        });
    }
}
