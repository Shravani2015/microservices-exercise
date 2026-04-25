package com.example.productservice.service;

import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        log.info("Creating product: {}", product.getName());
        return productRepository.save(product);
    }

    public Product getProductById(Integer id) {
        log.info("Fetching product with id: {}", id);

        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    public Product updateProduct(Integer id, Product product) {
        log.info("Updating product with id: {}", id);

        Product existingProduct = getProductById(id);

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Integer id) {
        log.info("Deleting product with id: {}", id);

        Product existingProduct = getProductById(id);
        productRepository.delete(existingProduct);
    }

    public Page<Product> getProductsWithPaginationAndSorting(
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(pageable);
    }

    public List<Product> searchProductsByName(String name) {
        log.info("Searching products by name: {}", name);

        return productRepository.findAll()
                .stream()
                .filter(product -> product.getName()
                        .toLowerCase()
                        .contains(name.toLowerCase()))
                .toList();
    }

    public List<Product> getProductsAbovePrice(Double price) {
        log.info("Fetching products above price: {}", price);
        return productRepository.findProductsAbovePrice(price);
    }

    public Boolean validateStock(Integer productId, Integer quantity) {
        Product product = getProductById(productId);

        boolean valid = product.getStock() >= quantity;

        log.info(
                "Stock validation for productId {} quantity {} result {}",
                productId,
                quantity,
                valid
        );

        return valid;
    }
}