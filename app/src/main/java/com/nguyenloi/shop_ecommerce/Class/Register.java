package com.nguyenloi.shop_ecommerce.Class;

public class Register {
    private String name, email, account_image;
    private int phone ;

    public Register() {
    }

    public Register(String name, String email, String account_image, String status, int phone) {
        this.name = name;
        this.email = email;
        this.account_image = account_image;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAccount_image() {
        return account_image;
    }


    public int getPhone() {
        return phone;
    }
}
