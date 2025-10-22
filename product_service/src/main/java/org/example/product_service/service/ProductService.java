package org.example.product_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.product_service.entity.Product;
import org.example.product_service.rabbitmq.OrderItem;
import org.example.product_service.repository.ProductRepository;
import org.example.product_service.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(final Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public ProductResponse getProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }

    public void rollbackProductQuantity(List<OrderItem> listItems) {
        for (OrderItem item : listItems) {
            productRepository.increaseQuantity(item.getProductId(), item.getQuantity());
        }
    }

    public boolean decreaseQuantity(List<OrderItem> listItems) {
        List<OrderItem> listItemsDecrease = new ArrayList<>();
        boolean allSuccess = true;
        for (OrderItem item : listItems) {
            int isSuccess = productRepository.decreaseQuantity(item.getProductId(), item.getQuantity());
            if (isSuccess == 0) {
                allSuccess = false;
                log.error("Product {} không đủ số lượng", item.getProductId());

                // rollback
                rollbackProductQuantity(listItemsDecrease);
                break;
            }
            else {
                listItemsDecrease.add(item);
            }
        }
        return allSuccess;
    }


}
