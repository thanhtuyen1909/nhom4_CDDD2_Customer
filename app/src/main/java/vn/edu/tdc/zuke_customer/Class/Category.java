package vn.edu.tdc.zuke_customer.Class;

public class Category {
   private String image,name, idCategory;

    public Category() {
    }

    public Category(String image, String name, String idCategory) {
        this.image = image;
        this.name = name;
        this.idCategory = idCategory;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getIdCategory() {
        return idCategory;
    }
}
