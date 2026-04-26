package com.example.productservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartEventConsumer {

    @KafkaListener(topics = "cart-events", groupId = "product-service-group")
    public void consumeCartEvent(String message) {
        log.info("====== KAFKA EVENT RECEIVED ======");
        log.info("Cart Event: {}", message);
        log.info("==================================");
    }
}