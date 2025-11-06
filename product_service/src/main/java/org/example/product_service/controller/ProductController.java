package org.example.product_service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.product_service.entity.Product;
import org.example.product_service.response.ProductResponse;
import org.example.product_service.service.ProductService;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/products"))
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final Environment env;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @PostMapping
    public Product save(@RequestBody final Product product) {
        return productService.createProduct(product);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable final int id) {
        String port = env.getProperty("local.server.port");
        log.info("Get product form port {}", port);
        return productService.getProductById(id);
    }
}
