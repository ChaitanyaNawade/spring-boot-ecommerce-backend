package com.chaitanya.jayganesh.controller;

import com.chaitanya.jayganesh.entity.Product;
import com.chaitanya.jayganesh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @PostMapping()
    public Product createProduct(@RequestBody Product product)
    {
       return productService.createProduct(product);
    }

    @GetMapping()
    public List<Product> getAllProduct()
    {
        return  productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id)
    {
        return productService.findProductById(id).
                map(ResponseEntity::ok).
                orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id)
    {
         productService.deleteProduct(id);
         return "product deleted";
    }
}
