package com.ordermanagement.controller;

import com.ordermanagement.dto.CartDto;
import com.ordermanagement.service.CartService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(@RequestBody Map<String, Object> request) {
        Long productId = Long.parseLong(request.get("productId").toString());
        Integer quantity = Integer.parseInt(request.get("quantity").toString());
        cartService.addProductToCart(productId, quantity);
        return ResponseEntity.ok(Map.of("message", "Product added to cart successfully."));
    }

    @GetMapping
    public ResponseEntity<CartDto> getCart() {
        CartDto cartDto = cartService.getCart();
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestBody Map<String, Object> request) {
        Long productId = Long.parseLong(request.get("productId").toString());
        Integer quantity = Integer.parseInt(request.get("quantity").toString());
        cartService.updateCartItem(productId, quantity);
        return ResponseEntity.ok(Map.of("message", "Cart updated successfully."));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeProductFromCart(@PathVariable Long productId) {
        cartService.removeProductFromCart(productId);
        return ResponseEntity.ok(Map.of("message", "Product removed from cart successfully."));
    }
}
