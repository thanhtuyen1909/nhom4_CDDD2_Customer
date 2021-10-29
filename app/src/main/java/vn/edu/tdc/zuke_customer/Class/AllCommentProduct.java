package vn.edu.tdc.zuke_customer.Class;

import java.util.ArrayList;

public class AllCommentProduct {
    private static ArrayList<RatingForUI> arrRatingUI;

    public AllCommentProduct() {
    }

    public static ArrayList<RatingForUI> getArrRatingUI() {
        return arrRatingUI;
    }

    public static void setArrRatingUI(ArrayList<RatingForUI> arrRatingUI) {
        AllCommentProduct.arrRatingUI = arrRatingUI;
    }
}
