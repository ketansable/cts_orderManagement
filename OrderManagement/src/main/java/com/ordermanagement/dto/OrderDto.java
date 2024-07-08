package com.ordermanagement.dto;

import lombok.Data;

import java.util.List;
import java.util.Date;

@Data
public class OrderDto {
    private Long id;
    private List<CartItemDto> cartItems;
    private Double totalOrderPrice;
    private String shippingAddress;
    private String paymentMethod;
    private String status;
    private Date orderDate;
}
