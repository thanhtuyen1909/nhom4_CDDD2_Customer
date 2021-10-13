package com.nguyenloi.shop_ecommerce;

import java.io.Serializable;

public class TheRating implements Serializable {
    private int imageBillRating;
    private String descriptionBillRating, nameProductBillRating,colorProductBillRating;

    public TheRating(int imageBillRating, String descriptionBillRating, String nameProductBillRating, String colorProductBillRating) {
        this.imageBillRating = imageBillRating;
        this.descriptionBillRating = descriptionBillRating;
        this.nameProductBillRating = nameProductBillRating;
        this.colorProductBillRating = colorProductBillRating;
    }

    public int getImageBillRating() {
        return imageBillRating;
    }

    public void setImageBillRating(int imageBillRating) {
        this.imageBillRating = imageBillRating;
    }

    public String getDescriptionBillRating() {
        return descriptionBillRating;
    }

    public void setDescriptionBillRating(String descriptionBillRating) {
        this.descriptionBillRating = descriptionBillRating;
    }

    public String getNameProductBillRating() {
        return nameProductBillRating;
    }

    public void setNameProductBillRating(String nameProductBillRating) {
        this.nameProductBillRating = nameProductBillRating;
    }

    public String getColorProductBillRating() {
        return colorProductBillRating;
    }

    public void setColorProductBillRating(String colorProductBillRating) {
        this.colorProductBillRating = colorProductBillRating;
    }
}
