package com.nguyenloi.shop_ecommerce.Class;

public class Cart_Detail {
    private int amount, totalPrice;
    private String productId, userId, keyCartDetail;

    public Cart_Detail() {
    }

    public Cart_Detail(int amount, int totalPrice, String productId, String userId, String keyCartDetail) {
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.productId = productId;
        this.userId = userId;
        this.keyCartDetail = keyCartDetail;
    }

    public int getAmount() {
        return amount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getProductId() {
        return productId;
    }

    public String getUserId() {
        return userId;
    }

    public String getKeyCartDetail() {
        return keyCartDetail;
    }
}
