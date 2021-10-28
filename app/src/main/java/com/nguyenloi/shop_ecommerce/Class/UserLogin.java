package com.nguyenloi.shop_ecommerce.Class;

public class UserLogin {
    private String password;
    private String username;

    public UserLogin() {
    }

    public UserLogin(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
