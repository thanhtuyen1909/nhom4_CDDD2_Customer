package com.nguyenloi.shop_ecommerce;

import java.io.Serializable;

public class TheBill implements Serializable {
    private  String status, bookingDate,receivedDate;

    public TheBill(String status, String bookingDate, String receivedDate) {
        this.status = status;
        this.bookingDate = bookingDate;
        this.receivedDate = receivedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }
}
