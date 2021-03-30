package com.aleslash.java.cartservice.repository;

import com.aleslash.java.cart.Cart;

public interface CartStore {
    void addItem(String userId, String productId, int quantity);
    void emptyCart(String userId);
    Cart getCart(String userId);

    boolean ping();
}
