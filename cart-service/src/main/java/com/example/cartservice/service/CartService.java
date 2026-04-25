package com.example.cartservice.service;

import com.example.cartservice.client.ProductClient;
import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.CartItem;
import com.example.cartservice.kafka.CartEventProducer;
import com.example.cartservice.repository.CartItemRepository;
import com.example.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductClient productClient;
    private final CartEventProducer cartEventProducer;

    public Cart createCart(Integer userId) {
        log.info("Creating cart for userId: {}", userId);
        Cart cart = Cart.builder().userId(userId).build();
        return cartRepository.save(cart);
    }

    public Cart getCartById(Integer cartId) {
        log.info("Fetching cart with id: {}", cartId);
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + cartId));
    }

    public CartItem addItemToCart(Integer cartId, Integer productId, Integer quantity) {
        log.info("Adding item to cart. cartId={}, productId={}, quantity={}",
                cartId, productId, quantity);

        // Run product fetch and stock validation in parallel
        CompletableFuture<Object> productFuture = CompletableFuture
                .supplyAsync(() -> productClient.getProductById(productId));

        CompletableFuture<Boolean> stockFuture = CompletableFuture
                .supplyAsync(() -> productClient.validateStock(productId, quantity));

        CompletableFuture.allOf(productFuture, stockFuture).join();

        Boolean stockValid = stockFuture.join();

        if (Boolean.FALSE.equals(stockValid)) {
            log.warn("Insufficient stock for productId: {}", productId);
            throw new RuntimeException("Insufficient stock for productId: " + productId);
        }

        Cart cart = getCartById(cartId);

        CartItem item = CartItem.builder()
                .cart(cart)
                .productId(productId)
                .quantity(quantity)
                .build();

        CartItem saved = cartItemRepository.save(item);

        // Publish Kafka event
        cartEventProducer.publishCartEvent(cartId, productId, quantity);

        return saved;
    }

    public List<CartItem> getItemsByCartId(Integer cartId) {
        log.info("Fetching items for cartId: {}", cartId);
        return cartItemRepository.findByCartId(cartId);
    }

    public void removeItemFromCart(Integer itemId) {
        log.info("Removing cart item with id: {}", itemId);
        cartItemRepository.deleteById(itemId);
    }
}
