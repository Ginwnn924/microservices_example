package org.example.product_service.controller;

import lombok.RequiredArgsConstructor;
import org.example.product_service.entity.Product;
import org.example.product_service.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/products"))
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @PostMapping
    public Product save(@RequestBody final Product product) {
        return productService.createProduct(product);
    }
}
