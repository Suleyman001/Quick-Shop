package nje.hu.quickshop.managers;

import java.util.ArrayList;
import java.util.List;

import nje.hu.quickshop.entities.CardItem;
import nje.hu.quickshop.entities.Product;

public class CartManager {
    private static CartManager instance;
    private final List<CardItem> cartItems = new ArrayList<>();

    private CartManager() {}

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Product product) {
        for (CardItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        cartItems.add(new CardItem(product, 1));
    }

    public List<CardItem> getCartItems() {
        return cartItems;
    }
}
