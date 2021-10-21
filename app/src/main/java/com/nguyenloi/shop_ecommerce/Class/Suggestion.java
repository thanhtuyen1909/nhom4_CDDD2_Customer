package com.nguyenloi.shop_ecommerce.Class;

public class Suggestion {
    private String suggestion, userId;

    public Suggestion(String suggestion, String userId) {
        this.suggestion = suggestion;
        this.userId = userId;
    }

    public Suggestion() {
    }

    public String getSuggestion() {
        return suggestion;
    }

    public String getUserId() {
        return userId;
    }
}
