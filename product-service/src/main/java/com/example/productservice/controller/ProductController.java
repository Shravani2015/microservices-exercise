package com.example.productservice.controller;

import com.example.productservice.entity.Product;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        log.info("POST /api/products");
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        log.info("GET /api/products/{}", id);
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("GET /api/products");
        return ResponseEntity.ok(productService.getAllProducts());
    }
    	
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Integer id,
            @Valid @RequestBody Product product) {
        log.info("PUT /api/products/{}", id);
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        log.info("DELETE /api/products/{}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Product>> getProductsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("GET /api/products/paginated - page={}, size={}", page, size);
        return ResponseEntity.ok(
                productService.getProductsWithPaginationAndSorting(page, size, sortBy, direction)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        log.info("GET /api/products/search?name={}", name);
        return ResponseEntity.ok(productService.searchProductsByName(name));
    }

    @GetMapping("/above-price")
    public ResponseEntity<List<Product>> getAbovePrice(@RequestParam Double price) {
        log.info("GET /api/products/above-price?price={}", price);
        return ResponseEntity.ok(productService.getProductsAbovePrice(price));
    }

    @GetMapping("/{id}/validate-stock")
    public ResponseEntity<Boolean> validateStock(
            @PathVariable Integer id,
            @RequestParam Integer quantity) {
                        log.info("GET /api/products/{}/validate-stock?quantity={}", id, quantity);
        return ResponseEntity.ok(productService.validateStock(id, quantity));
    }
}


