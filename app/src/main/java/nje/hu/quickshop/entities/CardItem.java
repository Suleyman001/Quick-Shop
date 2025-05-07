package nje.hu.quickshop.entities;

/*
quick reminder for pages
-activity_product_detail.xml: Product detail screen (shows a single product, works with Product).
fragment_cart.xml: Shows all products in the basket as a list (for example, a list of 3 products in the basket).
*/

public class CardItem {
    private Product product;
    private int quantity;

    public CardItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
