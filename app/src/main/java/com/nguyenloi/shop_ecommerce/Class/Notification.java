package com.nguyenloi.shop_ecommerce.Class;

public class Notification {
   private int imageNotification;
   private String titleNotification, descriptionNotification;

    public Notification(int imageNotification, String titleNotification, String descriptionNotification) {
        this.imageNotification = imageNotification;
        this.titleNotification = titleNotification;
        this.descriptionNotification = descriptionNotification;
    }

    public int getImageNotification() {
        return imageNotification;
    }

    public void setImageNotification(int imageNotification) {
        this.imageNotification = imageNotification;
    }

    public String getTitleNotification() {
        return titleNotification;
    }

    public void setTitleNotification(String titleNotification) {
        this.titleNotification = titleNotification;
    }

    public String getDescriptionNotification() {
        return descriptionNotification;
    }

    public void setDescriptionNotification(String descriptionNotification) {
        this.descriptionNotification = descriptionNotification;
    }
}
