package com.ecomm.backshop.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartId;

    @OneToOne
    private Product product;

    @OneToOne
    private User user;


    public Cart() {
    }

    public Cart(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
