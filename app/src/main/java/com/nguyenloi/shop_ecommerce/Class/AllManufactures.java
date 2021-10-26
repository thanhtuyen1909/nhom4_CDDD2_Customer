package com.nguyenloi.shop_ecommerce.Class;

import java.util.ArrayList;

public class AllManufactures {
    private static ArrayList<Manufactures> allManufactures;

    public AllManufactures() {
    }

    public static ArrayList<Manufactures> getAllManufactures() {
        return allManufactures;
    }

    public static void setAllManufactures(ArrayList<Manufactures> allManufactures) {
        AllManufactures.allManufactures = allManufactures;
    }
}
