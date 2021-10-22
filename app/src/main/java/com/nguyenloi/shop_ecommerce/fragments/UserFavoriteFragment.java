package com.nguyenloi.shop_ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nguyenloi.shop_ecommerce.Class.AllFavoriteUser;
import com.nguyenloi.shop_ecommerce.Class.Favorite;
import com.nguyenloi.shop_ecommerce.Class.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.Class.Products;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.activites.Cart.CartActivity;
import com.nguyenloi.shop_ecommerce.activites.Other.FindProductActivity;
import com.nguyenloi.shop_ecommerce.adapters.ProductsFavoriteAdapter;
import com.nguyenloi.shop_ecommerce.adapters.ProductsFindAdapter;

import java.util.ArrayList;

public class UserFavoriteFragment extends Fragment {
    Query queryByFavorite;
    ArrayList<Favorite> arrFavorite;
    ArrayList<Products> arrProduct;
    ProductsFavoriteAdapter productsFavoriteAdapter;
    RecyclerView rcvFavorite;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user_favorite,container,false);
        replaceLayoutMenuTop();
        setControl(view);
        arrFavorite=new ArrayList<>();
        arrProduct=new ArrayList<>();

        queryByFavorite = FirebaseDatabase.getInstance().getReference()
                .child("Favorite").orderByChild("userId").equalTo(GlobalIdUser.userId);

        //Load all data
        loadDataFavoriteUser();
        AllFavoriteUser.setArrAllFavoriteUser(arrFavorite);
        //set status for recycler view
        setRecylerView();
        //Load data favorite
        loadDataFavorite();

        return view;
    }

    private void setControl(View v) {
        rcvFavorite= v.findViewById(R.id.rcvFavorite);
    }
    private void  loadDataFavorite(){
       FirebaseDatabase.getInstance().getReference().child("Products").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot products: snapshot.getChildren()){
                   String productId = products.getKey();
                   for(int i=0;i< arrFavorite.size();i++){
                       if(productId.equals(arrFavorite.get(i).getProductId())){
                           arrProduct.add(products.getValue(Products.class));
                       }
                       productsFavoriteAdapter.notifyDataSetChanged();
                   }
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }

    private void setRecylerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);;
        rcvFavorite.setLayoutManager(linearLayoutManager);
        setDataChangeRecyclerView();
    }

    private void setDataChangeRecyclerView() {
        productsFavoriteAdapter = new ProductsFavoriteAdapter(getContext(), arrProduct);
        rcvFavorite.setAdapter(productsFavoriteAdapter);
    }


    private void loadDataFavoriteUser() {
        queryByFavorite.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot favorite : snapshot.getChildren()) {
                    arrFavorite.add(favorite.getValue(Favorite.class));

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void replaceLayoutMenuTop(){
        FragmentManager fragmentManager = getChildFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final SearchItemFragment newFragment = new SearchItemFragment();
        fragmentTransaction.replace(R.id.layoutFavorite, newFragment);
        fragmentTransaction.commit();
    }



}
