package com.nguyenloi.shop_ecommerce.Account;

public class Account {
    public Account() {
    }

    String key,password,stastus,username,AcountID;
    int role_id;

    public String getAcountID() {
        return AcountID;
    }

    public void setAcountID(String acountID) {
        AcountID = acountID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStastus() {
        return stastus;
    }

    public void setStastus(String stastus) {
        this.stastus = stastus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public Account(String key, String password, String stastus, String username,String acountID, int role_id) {
        this.key = key;
        this.password = password;
        this.stastus = stastus;
        this.username = username;
        this.AcountID=acountID;
        this.role_id = role_id;
    }
}
