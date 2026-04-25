package com.example.productservice.repository;

import com.example.productservice.entity.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameContainingIgnoreCase(String name);

    @Query(value = "SELECT * FROM products WHERE price > :price", nativeQuery = true)
    List<Product> findProductsAbovePrice(@Param("price") Double price);
}