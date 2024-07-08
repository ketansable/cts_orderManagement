package com.ordermanagement.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordermanagement.dto.CartDto;
import com.ordermanagement.dto.CartItemDto;
import com.ordermanagement.model.Cart;
import com.ordermanagement.model.CartItem;
import com.ordermanagement.model.Product;
import com.ordermanagement.repository.CartItemRepository;
import com.ordermanagement.repository.CartRepository;
import com.ordermanagement.repository.ProductRepository;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void addProductToCart(Long productId, int quantity) {
        Cart cart = getOrCreateCart();
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            Optional<CartItem> cartItemOpt = cart.getCartItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();

            if (cartItemOpt.isPresent()) {
                CartItem cartItem = cartItemOpt.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            } else {
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cart.getCartItems().add(cartItem);
            }

            cartRepository.save(cart);
        }
    }

    @Override
    public CartDto getCart() {
        Cart cart = getOrCreateCart();
        CartDto cartDto = new CartDto();
        cartDto.setCartItems(cart.getCartItems().stream().map(this::convertToDto).collect(Collectors.toList()));
        cartDto.setTotalCartPrice(cart.getTotalPrice());
        return cartDto;
    }

    @Override
    public void updateCartItem(Long productId, int quantity) {
        Cart cart = getOrCreateCart();
        cart.getCartItems().stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst()
            .ifPresent(cartItem -> {
                if (quantity <= 0) {
                    cart.getCartItems().remove(cartItem);
                } else {
                    cartItem.setQuantity(quantity);
                }
                cartRepository.save(cart);
            });
    }

    @Override
    public void removeProductFromCart(Long productId) {
        Cart cart = getOrCreateCart();
        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }

    private Cart getOrCreateCart() {
        // Logic to fetch the cart for the current user
        // For simplicity, assuming there's only one cart
        return cartRepository.findById(1L).orElseGet(() -> {
            Cart cart = new Cart();
            cartRepository.save(cart);
            return cart;
        });
    }

    private CartItemDto convertToDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setProductId(cartItem.getProduct().getId());
        dto.setName(cartItem.getProduct().getName());
        dto.setPrice(cartItem.getProduct().getPrice());
        dto.setQuantity(cartItem.getQuantity());
        dto.setTotalPrice(cartItem.getTotalPrice());
        return dto;
    }
}
