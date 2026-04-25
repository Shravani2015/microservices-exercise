package com.example.cartservice.controller;

import com.example.cartservice.dto.CartItemRequest;
import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.CartItem;
import com.example.cartservice.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestParam Integer userId) {
        log.info("POST /api/carts - Creating cart for userId: {}", userId);
        return ResponseEntity.ok(cartService.createCart(userId));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable Integer cartId) {
        log.info("GET /api/carts/{}", cartId);
        return ResponseEntity.ok(cartService.getCartById(cartId));
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItem> addItem(
            @PathVariable Integer cartId,
            @Valid @RequestBody CartItemRequest request) {
        log.info("POST /api/carts/{}/items", cartId);
        return ResponseEntity.ok(
                cartService.addItemToCart(cartId, request.getProductId(), request.getQuantity())
        );
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItem>> getItems(@PathVariable Integer cartId) {
        log.info("GET /api/carts/{}/items", cartId);
        return ResponseEntity.ok(cartService.getItemsByCartId(cartId));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<String> removeItem(@PathVariable Integer itemId) {
        log.info("DELETE /api/carts/items/{}", itemId);
        cartService.removeItemFromCart(itemId);
        return ResponseEntity.ok("Item removed successfully");
    }
}
