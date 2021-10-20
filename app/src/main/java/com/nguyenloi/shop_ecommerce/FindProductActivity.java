package com.nguyenloi.shop_ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nguyenloi.shop_ecommerce.adapters.ProductsFindAdapter;
import com.nguyenloi.shop_ecommerce.adapters.ProductsHomeCategoryAdapter;

import java.util.ArrayList;

public class FindProductActivity extends AppCompatActivity {
   RecyclerView rcvFindProducts;
   ProductsFindAdapter findAdapter;
   Query queryFilterData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_product);
        setControl();

        queryFilterData=FirebaseDatabase.getInstance().getReference().child("Products");
        //Load data
        loadDataFilter();
    }

    private void loadDataFilter(){
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(queryFilterData, Products.class)
                        .build();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FindProductActivity.this, LinearLayoutManager.VERTICAL, false);;
        rcvFindProducts.setLayoutManager(linearLayoutManager);
        findAdapter = new ProductsFindAdapter(options,FindProductActivity.this);
        rcvFindProducts.setAdapter(findAdapter);
    }




    private void setControl() {
        rcvFindProducts=findViewById(R.id.rcvFindProducts);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_find_product,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        findAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        findAdapter.stopListening();
    }

}