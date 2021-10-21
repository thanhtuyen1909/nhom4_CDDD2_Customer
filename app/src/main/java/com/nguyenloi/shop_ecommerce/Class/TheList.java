package com.nguyenloi.shop_ecommerce.Class;

import java.io.Serializable;

public class TheList implements Serializable {
    private int imageProduct,priceProduct, soldProduct;
    private String nameProduct;

    public TheList(int imageProduct, int priceProduct, int soldProduct, String nameProduct) {
        this.imageProduct = imageProduct;
        this.priceProduct = priceProduct;
        this.soldProduct = soldProduct;
        this.nameProduct = nameProduct;
    }

    public int getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(int imageProduct) {
        this.imageProduct = imageProduct;
    }

    public int getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(int priceProduct) {
        this.priceProduct = priceProduct;
    }

    public int getSoldProduct() {
        return soldProduct;
    }

    public void setSoldProduct(int soldProduct) {
        this.soldProduct = soldProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }
}
