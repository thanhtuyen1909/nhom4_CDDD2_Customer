package com.nguyenloi.shop_ecommerce.Class;

public class Favorite {
    private String productId, userId, key;


    public Favorite() {
    }

    public Favorite(String productId, String userId, String key) {
        this.productId = productId;
        this.userId = userId;
        this.key = key;
    }

    public String getProductId() {
        return productId;
    }

    public String getUserId() {
        return userId;
    }

    public String getKey() {
        return key;
    }


}
