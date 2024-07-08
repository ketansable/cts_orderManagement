package com.ordermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordermanagement.dto.OrderDto;
import com.ordermanagement.dto.OrderRequestDto;
import com.ordermanagement.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderDto orderDto = orderService.placeOrder(orderRequestDto);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) {
        OrderDto orderDto = orderService.getOrder(orderId);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }
}
