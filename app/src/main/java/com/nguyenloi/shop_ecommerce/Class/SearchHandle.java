package com.nguyenloi.shop_ecommerce.Class;

public class SearchHandle {
    public static String nameProduct, typeActivity, idCategory;

    public SearchHandle() {
    }

    public static String getNameProduct() {
        return nameProduct;
    }

    public static void setNameProduct(String nameProduct) {
        SearchHandle.nameProduct = nameProduct;
    }

    public static String getTypeActivity() {
        return typeActivity;
    }

    public static void setTypeActivity(String typeActivity) {
        SearchHandle.typeActivity = typeActivity;
    }

    public static String getIdCategory() {
        return idCategory;
    }

    public static void setIdCategory(String idCategory) {
        SearchHandle.idCategory = idCategory;
    }
}
