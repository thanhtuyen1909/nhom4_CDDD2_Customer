package vn.edu.tdc.zuke_customer.Class;

import java.io.Serializable;

public class CustomerConslutant implements Serializable {
    private String title, description;
    private boolean expand;

    public CustomerConslutant(String title, String description) {
        this.title = title;
        this.description = description;
        this.expand = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }
}
