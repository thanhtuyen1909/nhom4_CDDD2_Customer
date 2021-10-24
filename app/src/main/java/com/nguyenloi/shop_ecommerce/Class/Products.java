package com.nguyenloi.shop_ecommerce.Class;

public class Products {
    private String category_id, description, image, name, created_at, manu_id, key;
    private int quantity, import_price, sold, price;

    public Products() {
    }

    public Products(String category_id, String description, String image, String name, String created_at, String manu_id, String key, int quantity, int import_price, int sold, int price) {
        this.category_id = category_id;
        this.description = description;
        this.image = image;
        this.name = name;
        this.created_at = created_at;
        this.manu_id = manu_id;
        this.key = key;
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

    public String getCreated_at() {
        return created_at;
    }

    public String getManu_id() {
        return manu_id;
    }

    public String getKey() {
        return key;
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
