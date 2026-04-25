package com.example.cartservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartEventProducer {

    private static final String TOPIC = "cart-events";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishCartEvent(Integer cartId, Integer productId, Integer quantity) {
        String message = String.format(
                "{\"cartId\":%d,\"productId\":%d,\"quantity\":%d}",
                cartId, productId, quantity
        );

        log.info("Publishing cart event to Kafka: {}", message);
        kafkaTemplate.send(TOPIC, message);
        log.info("Cart event published successfully");
    }
}
