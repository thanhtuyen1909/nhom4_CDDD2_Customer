package vn.edu.tdc.zuke_customer.Class;

import java.util.ArrayList;

public class AllCategory {

    private static ArrayList<Category> arrAllCategory;

    public AllCategory() {
    }

    public static ArrayList<Category> getArrAllCategory() {
        return arrAllCategory;
    }

    public static void setArrAllCategory(ArrayList<Category> arrAllCategory) {
        AllCategory.arrAllCategory = arrAllCategory;
    }
}
