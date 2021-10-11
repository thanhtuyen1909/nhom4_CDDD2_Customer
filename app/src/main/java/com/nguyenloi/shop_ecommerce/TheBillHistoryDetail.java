package com.nguyenloi.shop_ecommerce;

import java.io.Serializable;

public class TheBillHistoryDetail implements Serializable {
    private int imgDetailBill, amountBill;
    private double priceBill, totalPriceBill;
    private String nameProduct;

    public TheBillHistoryDetail(int imgDetailBill, int amountBill, double priceBill, double totalPriceBill, String nameProduct) {
        this.imgDetailBill = imgDetailBill;
        this.amountBill = amountBill;
        this.priceBill = priceBill;
        this.totalPriceBill = totalPriceBill;
        this.nameProduct = nameProduct;
    }

    public int getImgDetailBill() {
        return imgDetailBill;
    }

    public void setImgDetailBill(int imgDetailBill) {
        this.imgDetailBill = imgDetailBill;
    }

    public int getAmountBill() {
        return amountBill;
    }

    public void setAmountBill(int amountBill) {
        this.amountBill = amountBill;
    }

    public double getPriceBill() {
        return priceBill;
    }

    public void setPriceBill(double priceBill) {
        this.priceBill = priceBill;
    }

    public double getTotalPriceBill() {
        return totalPriceBill;
    }

    public void setTotalPriceBill(double totalPriceBill) {
        this.totalPriceBill = totalPriceBill;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }
}
