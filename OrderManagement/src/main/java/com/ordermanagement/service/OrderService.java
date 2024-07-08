package com.ordermanagement.service;

import java.util.List;

import com.ordermanagement.dto.OrderDto;
import com.ordermanagement.dto.OrderRequestDto;

public interface OrderService {
    OrderDto placeOrder(OrderRequestDto orderRequestDto);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getOrders();
}

