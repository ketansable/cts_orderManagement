package com.ordermanagement.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ordermanagement.dto.CartItemDto;
import com.ordermanagement.dto.OrderDto;
import com.ordermanagement.dto.OrderRequestDto;
import com.ordermanagement.model.Cart;
import com.ordermanagement.model.Order;
import com.ordermanagement.repository.CartRepository;
import com.ordermanagement.repository.OrderRepository;
import com.ordermanagement.utils.EmailUtil;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private EmailUtil emailUtil;

    @Override
    public OrderDto placeOrder(OrderRequestDto orderRequestDto) {
        Cart cart = cartRepository.findById(orderRequestDto.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Order order = new Order();
        order.setCartItems(cart.getCartItems());
        order.setTotalOrderPrice(cart.getTotalPrice());
        order.setShippingAddress(orderRequestDto.getShippingAddress());
        order.setPaymentMethod(orderRequestDto.getPaymentMethod());
        order.setStatus("Pending");
        order.setOrderDate(new Date());
        orderRepository.save(order);

        // Send order confirmation email
        emailUtil.sendOrderConfirmation(order);

        return convertToDto(order);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDto(order);
    }

    @Override
    public List<OrderDto> getOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCartItems(order.getCartItems().stream().map(cartItem -> {
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setProductId(cartItem.getProduct().getId());
            cartItemDto.setName(cartItem.getProduct().getName());
            cartItemDto.setPrice(cartItem.getProduct().getPrice());
            cartItemDto.setQuantity(cartItem.getQuantity());
            cartItemDto.setTotalPrice(cartItem.getTotalPrice());
            return cartItemDto;
        }).collect(Collectors.toList()));
        dto.setTotalOrderPrice(order.getTotalOrderPrice());
        dto.setShippingAddress(order.getShippingAddress());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setStatus(order.getStatus());
        dto.setOrderDate(order.getOrderDate());
        return dto;
    }
}
