package com.nguyenloi.shop_ecommerce.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.nguyenloi.shop_ecommerce.Class.AllProducts;
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
    private String productId, userId, keyFavorite;
    Favorite favor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_favorite, container, false);
        replaceLayoutMenuTop();
        setControl(view);
        arrFavorite = new ArrayList<>();
        arrProduct = new ArrayList<>();

        queryByFavorite = FirebaseDatabase.getInstance().getReference()
                .child("Favorite").orderByChild("userId").equalTo(GlobalIdUser.userId);

        //Load all data
        loadDataFavoriteUser();

        //set status for recycler view
        setRecylerView();

        return view;
    }

    private void setControl(View v) {
        rcvFavorite = v.findViewById(R.id.rcvFavorite);
    }

    private void loadDataFavorite() {
        for (int i = 0; i < AllProducts.getArrAllProducts().size(); i++) {
            for (int j = 0; j < arrFavorite.size(); j++) {
                if (AllProducts.getArrAllProducts().get(i).getKey()
                        .equals(arrFavorite.get(j).getProductId())) {
                    arrProduct.add(AllProducts.getArrAllProducts().get(i));
                }
            }
        }
        setDataChangeRecyclerView();

    }


    private void setRecylerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ;
        rcvFavorite.setLayoutManager(linearLayoutManager);
        setDataChangeRecyclerView();
    }

    private void setDataChangeRecyclerView() {
        productsFavoriteAdapter = new ProductsFavoriteAdapter(getContext(), arrProduct);
        rcvFavorite.setAdapter(productsFavoriteAdapter);
    }


    private void loadDataFavoriteUser() {
        queryByFavorite.addValueEventListener(new ValueEventListener() {
            boolean processDone = false;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrFavorite.clear();
                if (!processDone && snapshot.exists()) {
                    arrFavorite.clear();
                    for (DataSnapshot favorite : snapshot.getChildren()) {
                        productId = favorite.getValue(Favorite.class).getProductId();
                        userId = favorite.getValue(Favorite.class).getUserId();
                        keyFavorite = favorite.getKey();
                        favor = new Favorite(productId, userId, keyFavorite);
                        arrFavorite.add(favor);
                        processDone = true;
                    }
                } else {
                    processDone = true;
                }
                //Filter data for recycler view
                if (processDone) {
                    loadDataFavorite();
                    AllFavoriteUser.setArrAllFavoriteUser(arrFavorite);

                    processDone = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void replaceLayoutMenuTop() {
        FragmentManager fragmentManager = getChildFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final SearchItemFragment newFragment = new SearchItemFragment();
        fragmentTransaction.replace(R.id.layoutFavorite, newFragment);
        fragmentTransaction.commit();
    }


}
