package com.chaitanya.jayganesh.service;

import com.chaitanya.jayganesh.entity.Product;
import com.chaitanya.jayganesh.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product)
    {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id)
    {
        return productRepository.findById(id);
    }

    public void deleteProduct(Long id)
    {
        productRepository.deleteById(id);
    }
}
