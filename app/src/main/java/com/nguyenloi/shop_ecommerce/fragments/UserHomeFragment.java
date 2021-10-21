package com.nguyenloi.shop_ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nguyenloi.shop_ecommerce.FindProductActivity;
import com.nguyenloi.shop_ecommerce.NotificationActiviy;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.activites.CartActivity;
import com.nguyenloi.shop_ecommerce.adapters.GridHomeAdapter;

import java.util.ArrayList;

public class UserHomeFragment extends Fragment {
    ArrayList<String> nameProduct = new ArrayList<>();
    ArrayList<String> priceProduct = new ArrayList<>();
    ArrayList<Integer> imgProduct = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user_home,container,false);
        setHasOptionsMenu(true);
        GridView gridView = view.findViewById(R.id.gridDataHome);
        fillArray();
        GridHomeAdapter adapter = new GridHomeAdapter(getContext(), nameProduct, priceProduct, imgProduct);
        gridView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home,menu);
         super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itCart:
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
                break;
            case R.id.itNotification:
                Intent intent2 = new Intent(getContext(), NotificationActiviy.class);
                startActivity(intent2);
            case R.id.itGoToFind:
                Intent intent3 = new Intent(getContext(), FindProductActivity.class);
                startActivity(intent3);
                break;

        }
        return super.onOptionsItemSelected(item);
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
