package org.example.product_service.repository;

import org.example.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.quantity = p.quantity - :quantity " +
            "WHERE p.id = :productId AND p.quantity >= :quantity")
    int decreaseQuantity(int productId, int quantity);


    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.quantity = p.quantity + :quantity " +
            "WHERE p.id = :productId")
    int increaseQuantity(int productId, int quantity);

}
