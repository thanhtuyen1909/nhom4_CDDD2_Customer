package com.nguyenloi.shop_ecommerce.adapters.category;

import com.nguyenloi.shop_ecommerce.Class.Products;

import java.util.ArrayList;

public class ListManufacturesProducts {
    private String nameManufactures;
    private ArrayList<Products> arrProducts;

    public ListManufacturesProducts() {
    }

    public ListManufacturesProducts(String nameManufactures, ArrayList<Products> arrProducts) {
        this.nameManufactures = nameManufactures;
        this.arrProducts = arrProducts;
    }

    public String getNameManufactures() {
        return nameManufactures;
    }

    public ArrayList<Products> getArrProducts() {
        return arrProducts;
    }
}
