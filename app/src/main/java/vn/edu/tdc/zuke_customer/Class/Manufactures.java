package vn.edu.tdc.zuke_customer.Class;

public class Manufactures {
    private String idManufactures, idCategory, image,name;

    public Manufactures() {
    }

    public Manufactures(String idManufactures, String idCategory, String image, String name) {
        this.idManufactures = idManufactures;
        this.idCategory = idCategory;
        this.image = image;
        this.name = name;
    }

    public String getIdManufactures() {
        return idManufactures;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
