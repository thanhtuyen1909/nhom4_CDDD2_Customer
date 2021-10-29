package vn.edu.tdc.zuke_customer.adapters.category;


import vn.edu.tdc.zuke_customer.Class.Products;

/**
 * Created by lenovo on 2/23/2016.
 */
public interface ItemClickListener {
    void itemClicked(Products item);
    void itemClicked(Section section);
}
