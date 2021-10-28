package com.nguyenloi.shop_ecommerce.Class;

public class Cart_Detail {
    private int amount,totalPrice;
    private String cartID, productID, carđDetailId;

    public Cart_Detail(int amount, int totalPrice, String cartID, String productID, String carđDetailId) {
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.cartID = cartID;
        this.productID = productID;
        this.carđDetailId = carđDetailId;
    }

    public Cart_Detail() {
    }

    public int getAmount() {
        return amount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getCartID() {
        return cartID;
    }

    public String getProductID() {
        return productID;
    }

    public String getCarđDetailId() {
        return carđDetailId;
    }
}
