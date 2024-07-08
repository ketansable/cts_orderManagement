package com.ordermanagement.service;

import com.ordermanagement.dto.CartDto;

public interface CartService {
    void addProductToCart(Long productId, int quantity);
    CartDto getCart();
    void updateCartItem(Long productId, int quantity);
    void removeProductFromCart(Long productId);
}
