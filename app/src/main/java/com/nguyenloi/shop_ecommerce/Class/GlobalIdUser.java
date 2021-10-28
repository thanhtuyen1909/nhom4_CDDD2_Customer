package com.nguyenloi.shop_ecommerce.Class;

 public  class   GlobalIdUser {
    public static String userId="";
    public static String customerId="";
    public static String cartId="";
    public static String phoneNumber="";
     public static String password="";

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

     public static String getCartId() {
         return cartId;
     }

     public static void setCartId(String cartId) {
         GlobalIdUser.cartId = cartId;
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
