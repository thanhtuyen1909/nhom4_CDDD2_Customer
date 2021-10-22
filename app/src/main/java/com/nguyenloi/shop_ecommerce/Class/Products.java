package com.nguyenloi.shop_ecommerce.Class;

public class Products {
    private String category_id, description, image, name, created_at, manu_id;
    private int quantity, import_price, sold, price;

    public Products() {
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
