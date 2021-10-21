package com.nguyenloi.shop_ecommerce.Class;

public class Favorite {
    private String productId, userId;

    public Favorite(String productId, String userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public Favorite() {
    }

    public String getProductId() {
        return productId;
    }

    public String getUserId() {
        return userId;
    }
}
