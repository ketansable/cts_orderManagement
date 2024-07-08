package com.ordermanagement.dto;

import lombok.Data;

@Data
public class OrderRequestDto {
    private Long cartId;
    private String shippingAddress;
    private String paymentMethod;
}
