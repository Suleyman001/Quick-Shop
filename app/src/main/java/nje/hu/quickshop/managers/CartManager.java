// CartManager.java
package nje.hu.quickshop.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nje.hu.quickshop.entities.CardItem;
import nje.hu.quickshop.entities.Product;

public class CartManager {
    private static CartManager instance;
    private HashMap<Integer, CardItem> cartMap;

    private CartManager() {
        cartMap = new HashMap<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Product product) {
        if (cartMap.containsKey(product.getId())) {
            CardItem existingItem = cartMap.get(product.getId());
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            cartMap.put(product.getId(), new CardItem(product, 1));
        }
    }

    public List<CardItem> getCartItems() {
        return new ArrayList<>(cartMap.values());
    }

    public void removeProductFromCart(int productId) {
        cartMap.remove(productId);
    }


    public void clearCart() {
        cartMap.clear();
    }
}
