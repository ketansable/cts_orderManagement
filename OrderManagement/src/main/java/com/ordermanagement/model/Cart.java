package com.ordermanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
    
    public Double getTotalPrice() {
        return cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }
}
