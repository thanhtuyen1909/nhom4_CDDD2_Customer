package com.nguyenloi.shop_ecommerce.activites.Other;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.nguyenloi.shop_ecommerce.R;

import java.util.ArrayList;

public class DetailProductActivity extends AppCompatActivity {
    ArrayList<String> nameProduct = new ArrayList<>();
    ArrayList<String> priceProduct = new ArrayList<>();
    ArrayList<Integer> imgProduct = new ArrayList<>();
    GridView girdDetailProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        this.getSupportActionBar().setTitle("Chi tiết sản phẩm");
        setControl();
    }

    private void setControl() {
        girdDetailProduct=findViewById(R.id.gridDetailProduct);
        fillArray();

    }

    private void fillArray() {
        nameProduct.add("MacBook");
        nameProduct.add("Asus");
        nameProduct.add("Lenovo");
        nameProduct.add("Dell");
        nameProduct.add("Laptop HP");
        nameProduct.add("MacBook Pro");

        priceProduct.add("1200000");
        priceProduct.add("1100000");
        priceProduct.add("900000");
        priceProduct.add("1400000");
        priceProduct.add("1320000");
        priceProduct.add("2000000");

        imgProduct.add(R.drawable.lap6);
        imgProduct.add(R.drawable.lap5);
        imgProduct.add(R.drawable.lap4);
        imgProduct.add(R.drawable.lap3);
        imgProduct.add(R.drawable.lap2);
        imgProduct.add(R.drawable.lap1);
    }
}