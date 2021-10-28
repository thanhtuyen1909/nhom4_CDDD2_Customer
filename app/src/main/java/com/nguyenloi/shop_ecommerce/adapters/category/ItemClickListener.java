package com.nguyenloi.shop_ecommerce.adapters.category;


import com.nguyenloi.shop_ecommerce.Class.Products;

/**
 * Created by lenovo on 2/23/2016.
 */
public interface ItemClickListener {
    void itemClicked(Products item);
    void itemClicked(Section section);
}
