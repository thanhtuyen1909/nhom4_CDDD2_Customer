package com.nguyenloi.shop_ecommerce.Class;

public class Order_Details {
    private String idOrderDetail, productID, orderID;
    private int amount, price;

    public Order_Details(String idOrderDetail, String productID, String orderID, int amount, int price) {
        this.idOrderDetail = idOrderDetail;
        this.productID = productID;
        this.orderID = orderID;
        this.amount = amount;
        this.price = price;
    }

    public Order_Details() {
    }

    public String getIdOrderDetail() {
        return idOrderDetail;
    }

    public String getProductID() {
        return productID;
    }

    public String getOrderID() {
        return orderID;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }
}
