package com.nguyenloi.shop_ecommerce.Class;

 public  class   GlobalIdUser {
    public static String userId="abc0568442815";
    public static String customerId="-Mn-ErvarWALssFzgmSl5";
    public static String orderId="-Mn-M4tOnws1obCjLkdF";
    public static String phoneNumber="0568442815";
     public static String password="Hongloi1";

    public GlobalIdUser() {
    }

    public  String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

     public static String getCustomerId() {
         return customerId;
     }

     public static void setCustomerId(String customerId) {
         GlobalIdUser.customerId = customerId;
     }

     public static String getOrderId() {
         return orderId;
     }

     public static void setOrderId(String orderId) {
         GlobalIdUser.orderId = orderId;
     }

     public static String getPhoneNumber() {
         return phoneNumber;
     }

     public static void setPhoneNumber(String phoneNumber) {
         GlobalIdUser.phoneNumber = phoneNumber;
     }

     public static String getPassword() {
         return password;
     }

     public static void setPassword(String password) {
         GlobalIdUser.password = password;
     }
 }
