package com.nguyenloi.shop_ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nguyenloi.shop_ecommerce.Class.Category;
import com.nguyenloi.shop_ecommerce.Class.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.activites.Other.FindProductActivity;
import com.nguyenloi.shop_ecommerce.activites.Other.NotificationActiviy;
import com.nguyenloi.shop_ecommerce.Class.Products;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.activites.Cart.CartActivity;
import com.nguyenloi.shop_ecommerce.adapters.ProductsHomeCategoryAdapter;
import com.nguyenloi.shop_ecommerce.adapters.ProductsHomeSuggestionAdapter;
import com.nguyenloi.shop_ecommerce.adapters.ProductsHomeTopSoldAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserHomeFragment extends Fragment {
    RecyclerView rcvHomeTopSold, rcvHomeSuggestion, rcvHomeCategory;
    ImageSlider imgHomeSlider;
    AutoCompleteTextView edtAutoCompleted;
    ImageView imgIconHomeShop, imgIconHomeNotification;

    ProductsHomeTopSoldAdapter topSoldAdapter;
    ProductsHomeSuggestionAdapter suggestionAdapter;
    ProductsHomeCategoryAdapter categoryAdapter;


    Query querySortBySold, querySortBySuggestion, queryByCategory, querySortBySoldTop4, queryBySuggestion;
    List<SlideModel> arrListAds;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        setHasOptionsMenu(true);
        setControl(view);

        //Load data search
        arrListAds = new ArrayList<>();


        //Query
        querySortBySold = FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("sold").limitToFirst(20);
        querySortBySoldTop4 = FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("sold").limitToFirst(4);
        querySortBySuggestion = FirebaseDatabase.getInstance().getReference().child("Products").limitToFirst(10);
        queryByCategory = FirebaseDatabase.getInstance().getReference().child("Manufactures");
        queryBySuggestion = FirebaseDatabase.getInstance().getReference().
                child("AutocompleteSuggesstion").orderByChild("userId").equalTo(GlobalIdUser.userId);

        //Load data

        loadDataByTopSold();
        loadDataSuggestion();
        loadDataByCategory();
        loadDataAds();
        loadDataSearch();

         //Tab on keyboard done
        edtAutoCompleted.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtAutoCompleted.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                   checkIsSuggesstion();
                   Intent i =new Intent(getContext(), FindProductActivity.class);
                   i.putExtra("nameProduct", edtAutoCompleted.getText().toString());
                    Toast.makeText(getContext(), "send "+edtAutoCompleted.getText().toString(), Toast.LENGTH_SHORT).show();
                   startActivity(i);
                    return true;
                }
                return false;
            }
        });

        //Tab on icon notification
        imgIconHomeNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FindProductActivity.class);
                startActivity(intent);
            }
        });
        return view;
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
        edtAutoCompleted = v.findViewById(R.id.idAutoCompleted);
        imgIconHomeNotification = v.findViewById(R.id.imgIconHomeNotification);
        imgIconHomeShop = v.findViewById(R.id.imgIconHomeShop);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //Load data search by this user
    private void loadDataSearch() {

        final ArrayAdapter<String> autoComplete = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        queryBySuggestion.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot suggestionSnapshot : snapshot.getChildren()) {
                        String suggestion = suggestionSnapshot.child("suggestion").getValue(String.class);
                        autoComplete.add(suggestion);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        edtAutoCompleted.setAdapter(autoComplete);

    }

    private void checkIsSuggesstion(){
        FirebaseDatabase.getInstance().getReference().
                child("AutocompleteSuggesstion").addValueEventListener(new ValueEventListener() {
            boolean processDone = false;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot suggestionSnapshot : snapshot.getChildren()) {
                    String suggestion = suggestionSnapshot.child("suggestion").getValue(String.class);
                    String userId = suggestionSnapshot.child("userId").getValue(String.class);
                    if(edtAutoCompleted.getText().toString().trim().equals(suggestion)
                            &&GlobalIdUser.userId.equals(userId)
                            &&!processDone){
                        processDone=true;
                    }
                }
                if(!processDone){
                    insertToSuggesstionForUser(edtAutoCompleted.getText().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void insertToSuggesstionForUser(String strName) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", GlobalIdUser.userId);
        map.put("suggestion", strName);
        FirebaseDatabase.getInstance().getReference().child("AutocompleteSuggesstion")
                .push().setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
        topSoldAdapter.stopListening();
        ;
        suggestionAdapter.stopListening();
    }

}
