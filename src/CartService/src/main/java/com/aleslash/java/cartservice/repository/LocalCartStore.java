package com.aleslash.java.cartservice.repository;

import com.aleslash.java.cart.Cart;
import com.aleslash.java.cart.CartItem;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class LocalCartStore implements CartStore{

    private final Cart emptyCart = Cart.newBuilder().build();

    private Map<String,Cart> userCartItems = new ConcurrentHashMap<>();

    @Override
    public void addItem(String userId, String productId, int quantity) {
        Cart newCart = Cart.newBuilder()
                .setUserId(userId)
                .addItems(CartItem.newBuilder()
                        .setProductId(productId)
                        .setQuantity(quantity)
                        .build())
                .build();
        userCartItems.merge(userId,newCart,(cartOriginal, cartNovo) -> {
            Optional<CartItem> existingItem = cartOriginal.getItemsList().stream()
                    .filter(cartItem -> cartItem.getProductId().equalsIgnoreCase(productId)).collect(Collectors.toList()).stream().findFirst();
            CartItem cartItem;
            if(existingItem.isPresent()) {
                cartItem = CartItem.newBuilder()
                        .setQuantity(existingItem.get().getQuantity() + quantity)
                        .setProductId(productId)
                        .build();
            }
            else {
                cartItem = CartItem.newBuilder()
                        .setQuantity(quantity)
                        .setProductId(productId)
                        .build();
            }
            Cart cartPosMerge = Cart.newBuilder()
                    .addAllItems(cartOriginal.getItemsList()
                            .stream().filter(items -> !items.getProductId().equalsIgnoreCase(productId)).collect(Collectors.toList()))
                    .addItems(cartItem).build();
            return cartPosMerge;
        });
    }

    @Override
    public void emptyCart(String userId) {
        userCartItems.remove(userId);
        userCartItems.put(userId,emptyCart);
    }

    @Override
    public Cart getCart(String userId) {
        return userCartItems.getOrDefault(userId,emptyCart);
    }

    @Override
    public boolean ping() {
        return true;
    }
}
