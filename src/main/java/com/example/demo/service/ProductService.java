package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAll(Sort.by("id"));
        }
        return productRepository.findByNameContainingIgnoreCase(keyword.trim());
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void update(Product updatedProduct) {
        productRepository.findById(updatedProduct.getId()).ifPresent(product -> {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());
            product.setImageName(updatedProduct.getImageName());
            productRepository.save(product);
        });
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
