package com.ordermanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Date;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    private Double totalOrderPrice;
    private String shippingAddress;
    private String paymentMethod;
    private String status;
    private Date orderDate;
}