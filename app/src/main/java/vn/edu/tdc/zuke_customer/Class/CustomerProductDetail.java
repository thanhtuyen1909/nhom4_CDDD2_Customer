package vn.edu.tdc.zuke_customer.Class;

public class CustomerProductDetail {
    private String id, idCustomer,image,name;

    public CustomerProductDetail(String id, String idCustomer, String image, String name) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.image = image;
        this.name = name;
    }

    public CustomerProductDetail() {
    }

    public String getId() {
        return id;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
