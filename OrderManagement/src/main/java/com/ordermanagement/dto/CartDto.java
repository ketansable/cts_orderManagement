package com.ordermanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {
    private List<CartItemDto> cartItems;
    private Double totalCartPrice;
}
