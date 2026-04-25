package com.example.cartservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductClient {

    private final WebClient webClient;

    public Boolean validateStock(Integer productId, Integer quantity) {
        log.info("Calling Product Service to validate stock for productId: {}", productId);

        return webClient.get()
                .uri("/api/products/{id}/validate-stock?quantity={qty}",
                        productId, quantity)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

    public Object getProductById(Integer productId) {
        log.info("Fetching product details for productId: {}", productId);

        return webClient.get()
                .uri("/api/products/{id}", productId)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
