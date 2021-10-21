package com.nguyenloi.shop_ecommerce.activites.Other;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nguyenloi.shop_ecommerce.Class.Products;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.activites.Login.RegisterActivity;
import com.nguyenloi.shop_ecommerce.adapters.ProductsFindAdapter;

import java.util.ArrayList;

public class FindProductActivity extends AppCompatActivity {
    RecyclerView rcvFindProducts;
    ProductsFindAdapter findAdapter;
    ImageView imgFindArrowBack;
    TextView tvFindSearchTitle;
    AutoCompleteTextView edtFindAutocompleted;
    String nameProduct;
    private ArrayList<Products> arrProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_product);
        setControl();
        this.getSupportActionBar().hide();
        //Get and set name from search main
        Intent data = getIntent();
        data.getStringExtra("nameProduct");
        edtFindAutocompleted.setText(nameProduct);
        Toast.makeText(FindProductActivity.this, "as"+nameProduct, Toast.LENGTH_SHORT).show();

        arrProducts = new ArrayList<>();
        //Load data
        loadDataFilter();

        imgFindArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadDataFilter() {
        addDataArrayFilterFromDatabase();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FindProductActivity.this, LinearLayoutManager.VERTICAL, false);
        ;
        rcvFindProducts.setLayoutManager(linearLayoutManager);
        findAdapter = new ProductsFindAdapter(FindProductActivity.this, arrProducts);
        rcvFindProducts.setAdapter(findAdapter);
    }

    private void addDataArrayFilterFromDatabase() {
        FirebaseDatabase.getInstance().getReference().child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot products : snapshot.getChildren()) {
                    arrProducts.add(products.getValue(Products.class));
                }
                findAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void setControl() {
        imgFindArrowBack = findViewById(R.id.imgFindArrowBack);
        tvFindSearchTitle = findViewById(R.id.tvFindSearchTitle);
        rcvFindProducts = findViewById(R.id.rcvFindProducts);
        edtFindAutocompleted=findViewById(R.id.edtFindAutocompleted);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_find_product, menu);
        return super.onCreateOptionsMenu(menu);
    }


}