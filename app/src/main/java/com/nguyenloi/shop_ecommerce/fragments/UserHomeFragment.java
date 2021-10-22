package com.nguyenloi.shop_ecommerce.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nguyenloi.shop_ecommerce.Class.AllProducts;
import com.nguyenloi.shop_ecommerce.Class.Category;
import com.nguyenloi.shop_ecommerce.Class.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.Class.Products;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.adapters.ProductsHomeCategoryAdapter;
import com.nguyenloi.shop_ecommerce.adapters.ProductsHomeSuggestionAdapter;
import com.nguyenloi.shop_ecommerce.adapters.ProductsHomeTopSoldAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserHomeFragment extends Fragment {
    RecyclerView rcvHomeTopSold, rcvHomeSuggestion, rcvHomeCategory;
    ImageSlider imgHomeSlider;

    ProductsHomeTopSoldAdapter topSoldAdapter;
    ProductsHomeSuggestionAdapter suggestionAdapter;
    ProductsHomeCategoryAdapter categoryAdapter;


    Query querySortBySold, querySortBySuggestion, queryByCategory, querySortBySoldTop4, queryBySuggestion;
    List<SlideModel> arrListAds;
    ArrayList<Products> arrProducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        setHasOptionsMenu(true);
        setControl(view);

        //Load data search
        arrListAds = new ArrayList<>();
        arrProducts = new ArrayList<>();

        //Query
        querySortBySold = FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("sold").limitToFirst(20);
        querySortBySoldTop4 = FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("sold").limitToFirst(4);
        querySortBySuggestion = FirebaseDatabase.getInstance().getReference().child("Products").limitToFirst(10);
        queryByCategory = FirebaseDatabase.getInstance().getReference().child("Categories");
        queryBySuggestion = FirebaseDatabase.getInstance().getReference().
                child("AutocompleteSuggesstion").orderByChild("userId").equalTo(GlobalIdUser.userId);

        //Load data
        loadDataByTopSold();
        loadDataSuggestion();
        loadDataByCategory();
        loadDataAds();
        //Load all products
        loadAllProducts();
        AllProducts.setArrAllProducts(arrProducts);
         //Tab on keyboard done
        replaceLayoutMenuTop();
        return view;
    }

    private void loadAllProducts() {
        FirebaseDatabase.getInstance().getReference().child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot products : snapshot.getChildren()) {
                   arrProducts.add(products.getValue(Products.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadDataAds() {
        arrListAds.add(new SlideModel("https://www.pngall.com/wp-content/uploads/2016/06/Ecommerce-Download-PNG.png", "Nhanh chóng"));
        arrListAds.add(new SlideModel("https://www.pinclipart.com/picdir/big/356-3561245_ecommerce-pic-online-shopping-clipart-png-transparent-png.png", "Tiện lợi"));
        arrListAds.add(new SlideModel("https://www.pngplay.com/wp-content/uploads/6/E-Commerce-Logo-PNG-Clipart-Background.png", "Thanh toán dễ dàng"));
        arrListAds.add(new SlideModel("https://www.pinclipart.com/picdir/big/567-5679767_online-shopping-png-clipart.png", "An toàn"));
        imgHomeSlider.setImageList(arrListAds, true);
    }


    private void loadDataByTopSold() {
        //Set type rcvView
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(rcvHomeTopSold.VERTICAL);
        rcvHomeTopSold.setLayoutManager(layoutManager);
        //Load data
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(querySortBySold, Products.class)
                        .build();
        topSoldAdapter = new ProductsHomeTopSoldAdapter(options, getContext());
        rcvHomeTopSold.setAdapter(topSoldAdapter);
    }

    private void loadDataSuggestion() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvHomeSuggestion.setLayoutManager(layoutManager);
        rcvHomeSuggestion.setAdapter(suggestionAdapter);

        //Set type rcvView

        //Load data
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(querySortBySuggestion, Products.class)
                        .build();
        suggestionAdapter = new ProductsHomeSuggestionAdapter(options, getContext());
        rcvHomeSuggestion.setAdapter(suggestionAdapter);
    }

    private void loadDataByCategory() {
        //Set type rcvView
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(rcvHomeCategory.HORIZONTAL);
        rcvHomeCategory.setLayoutManager(layoutManager);
        //Load data
        FirebaseRecyclerOptions<Category> options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(queryByCategory, Category.class)
                        .build();
        categoryAdapter = new ProductsHomeCategoryAdapter(options, getContext());
        rcvHomeCategory.setAdapter(categoryAdapter);
    }

    private void setControl(View v) {
        rcvHomeTopSold = v.findViewById(R.id.rcvHomeTopSold);
        rcvHomeSuggestion = v.findViewById(R.id.rcvHomeSuggestion);
        rcvHomeCategory = v.findViewById(R.id.rcvHomeCategory);
        imgHomeSlider = v.findViewById(R.id.imgHomeSlider);
    }

    private void replaceLayoutMenuTop(){
        FragmentManager fragmentManager = getChildFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final SearchItemFragment newFragment = new SearchItemFragment();
        fragmentTransaction.replace(R.id.layoutHome, newFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        categoryAdapter.startListening();
        topSoldAdapter.startListening();
        suggestionAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        categoryAdapter.stopListening();
        topSoldAdapter.stopListening();
        ;
        suggestionAdapter.stopListening();
    }

}
