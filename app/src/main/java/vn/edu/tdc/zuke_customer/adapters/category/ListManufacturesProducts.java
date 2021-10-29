package vn.edu.tdc.zuke_customer.adapters.category;

import vn.edu.tdc.zuke_customer.Class.Products;

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
