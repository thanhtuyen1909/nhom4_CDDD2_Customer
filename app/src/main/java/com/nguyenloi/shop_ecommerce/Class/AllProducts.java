package com.nguyenloi.shop_ecommerce.Class;

import java.util.ArrayList;

public class AllProducts {
    private static ArrayList<Products> arrAllProducts;

    public AllProducts() {
    }

    public static ArrayList<Products> getArrAllProducts() {
        return arrAllProducts;
    }

    public static void setArrAllProducts(ArrayList<Products> arrAllProducts) {
        AllProducts.arrAllProducts = arrAllProducts;
    }
}
