package com.nguyenloi.shop_ecommerce.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nguyenloi.shop_ecommerce.Category;
import com.nguyenloi.shop_ecommerce.FindProductActivity;
import com.nguyenloi.shop_ecommerce.NotificationActiviy;
import com.nguyenloi.shop_ecommerce.Products;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.activites.CartActivity;
import com.nguyenloi.shop_ecommerce.adapters.ProductsHomeCategoryAdapter;
import com.nguyenloi.shop_ecommerce.adapters.ProductsHomeSuggestionAdapter;
import com.nguyenloi.shop_ecommerce.adapters.ProductsHomeTopSoldAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserHomeFragment extends Fragment {
    RecyclerView rcvHomeTopSold, rcvHomeSuggestion, rcvHomeCategory;
    ImageSlider imgHomeSlider;

    ProductsHomeTopSoldAdapter topSoldAdapter;
    ProductsHomeSuggestionAdapter suggestionAdapter;
    ProductsHomeCategoryAdapter categoryAdapter;

    Query querySortBySold,querySortBySuggestion, queryByCategory, querySortBySoldTop4;
    List<SlideModel> arrListAds;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user_home,container,false);
        setHasOptionsMenu(true);
        setControl(view);

        arrListAds = new ArrayList<>();

        //Query
        querySortBySold=FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("sold").limitToFirst(20);
        querySortBySoldTop4=FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("sold").limitToFirst(4);
        querySortBySuggestion=FirebaseDatabase.getInstance().getReference().child("Products").limitToFirst(10);
        queryByCategory=FirebaseDatabase.getInstance().getReference().child("Manufactures");

        //Load data

        loadDataByTopSold();
        loadDataSuggestion();
        loadDataByCategory();
        loadDataAds();

        return view;
    }

    private void loadDataAds() {
        arrListAds.add(new SlideModel("https://www.pngall.com/wp-content/uploads/2016/06/Ecommerce-Download-PNG.png", "Nhanh chóng"));
        arrListAds.add(new SlideModel("https://www.pinclipart.com/picdir/big/356-3561245_ecommerce-pic-online-shopping-clipart-png-transparent-png.png", "Tiện lợi"));
        arrListAds.add(new SlideModel("https://www.pngplay.com/wp-content/uploads/6/E-Commerce-Logo-PNG-Clipart-Background.png", "Thanh toán dễ dàng"));
        arrListAds.add(new SlideModel("https://www.pinclipart.com/picdir/big/567-5679767_online-shopping-png-clipart.png", "An toàn"));
        imgHomeSlider.setImageList(arrListAds,true);
    }


    private void loadDataByTopSold(){
        //Set type rcvView
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        layoutManager.setOrientation(rcvHomeTopSold.VERTICAL);
        rcvHomeTopSold.setLayoutManager(layoutManager);
        //Load data
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(querySortBySold, Products.class)
                        .build();
        topSoldAdapter = new ProductsHomeTopSoldAdapter(options,getContext());
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
        suggestionAdapter = new ProductsHomeSuggestionAdapter(options,getContext());
        rcvHomeSuggestion.setAdapter(suggestionAdapter);
    }

    private void loadDataByCategory(){
        //Set type rcvView
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        layoutManager.setOrientation(rcvHomeCategory.HORIZONTAL);
        rcvHomeCategory.setLayoutManager(layoutManager);
        //Load data
        FirebaseRecyclerOptions<Category> options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(queryByCategory, Category.class)
                        .build();
        categoryAdapter = new ProductsHomeCategoryAdapter(options,getContext());
        rcvHomeCategory.setAdapter(categoryAdapter);
    }


    private void setControl(View v){
        rcvHomeTopSold=v.findViewById(R.id.rcvHomeTopSold);
        rcvHomeSuggestion=v.findViewById(R.id.rcvHomeSuggestion);
        rcvHomeCategory=v.findViewById(R.id.rcvHomeCategory);
        imgHomeSlider=v.findViewById(R.id.imgHomeSlider);
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
        topSoldAdapter.stopListening();;
        suggestionAdapter.stopListening();
    }

}
