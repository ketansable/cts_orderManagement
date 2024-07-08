package com.ordermanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private Integer quantity;

    @ManyToOne
    private Cart cart;
    
    public Double getTotalPrice() {
        return this.quantity * this.product.getPrice();
    }
}
