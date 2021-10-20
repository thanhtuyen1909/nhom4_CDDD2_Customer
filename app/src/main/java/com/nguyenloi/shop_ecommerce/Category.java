package com.nguyenloi.shop_ecommerce;

public class Category {
   private String image,name;

    public Category() {
    }

    public Category(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
