package com.nguyenloi.shop_ecommerce.Class;

public class RatingForUI {
    private String  commentDescription, nameUser, imageUser;
    private int numStar;

    public RatingForUI(String commentDescription, String nameUser, String imageUser, int numStar) {
        this.commentDescription = commentDescription;
        this.nameUser = nameUser;
        this.imageUser = imageUser;
        this.numStar = numStar;
    }

    public RatingForUI() {
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getImageUser() {
        return imageUser;
    }

    public int getNumStar() {
        return numStar;
    }
}
