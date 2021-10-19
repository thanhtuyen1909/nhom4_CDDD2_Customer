package com.nguyenloi.shop_ecommerce;

public class UserLogin {
    private String password;
    private String phone;

    public UserLogin() {
    }

    public UserLogin(String password, String phone) {
        this.password = password;
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }
}
