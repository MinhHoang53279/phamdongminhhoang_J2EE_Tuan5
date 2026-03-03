package com.example.demo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.demo.model.Product;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public List<Product> findAll(String keyword) {
        String normalized = keyword == null ? "" : keyword.trim().toLowerCase(Locale.ROOT);
        return products.stream()
            .filter(product -> normalized.isEmpty() || product.getName().toLowerCase(Locale.ROOT).contains(normalized))
            .sorted(Comparator.comparing(Product::getId))
            .toList();
    }

    public Product create(Product product) {
        product.setId(sequence.getAndIncrement());
        products.add(product);
        return product;
    }

    public Optional<Product> findById(Long id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst();
    }

    public void update(Product updatedProduct) {
        findById(updatedProduct.getId()).ifPresent(product -> {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());
            product.setImageName(updatedProduct.getImageName());
        });
    }

    public void deleteById(Long id) {
        products.removeIf(product -> product.getId().equals(id));
    }
}
