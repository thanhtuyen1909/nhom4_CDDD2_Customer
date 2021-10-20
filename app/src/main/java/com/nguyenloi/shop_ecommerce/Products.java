package com.nguyenloi.shop_ecommerce;

public class Products {
    private String category_id, description, image, name;
    private int quantity, import_price, sold, price;

    public Products() {
    }

    public Products(String category_id, String description, String image, String name, int quantity, int import_price, int sold, int price) {
        this.category_id = category_id;
        this.description = description;
        this.image = image;
        this.name = name;
        this.quantity = quantity;
        this.import_price = import_price;
        this.sold = sold;
        this.price = price;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImport_price() {
        return import_price;
    }

    public int getSold() {
        return sold;
    }

    public int getPrice() {
        return price;
    }
}
