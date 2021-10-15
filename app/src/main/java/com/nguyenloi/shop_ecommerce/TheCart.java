package com.nguyenloi.shop_ecommerce;

import java.io.Serializable;

public class TheCart implements Serializable {
    private double price ;
    private int amout, image;
    private String name;

    public TheCart(double price, int amout, int image, String name) {
        this.price = price;
        this.amout = amout;
        this.image = image;
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmout() {
        return amout;
    }

    public void setAmout(int amout) {
        this.amout = amout;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
