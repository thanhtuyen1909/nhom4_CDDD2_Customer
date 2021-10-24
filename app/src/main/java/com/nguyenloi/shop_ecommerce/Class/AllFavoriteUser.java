package com.nguyenloi.shop_ecommerce.Class;

import java.util.ArrayList;

public class AllFavoriteUser {
    private static ArrayList<Favorite> arrAllFavoriteUser;

    public AllFavoriteUser() {
    }


    public static ArrayList<Favorite> getArrAllFavoriteUser() {
        return arrAllFavoriteUser;
    }

    public static void setArrAllFavoriteUser(ArrayList<Favorite> arrAllFavoriteUser) {
        AllFavoriteUser.arrAllFavoriteUser = arrAllFavoriteUser;
    }
}
