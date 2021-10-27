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
import com.nguyenloi.shop_ecommerce.Class.AllCategory;
import com.nguyenloi.shop_ecommerce.Class.AllManufactures;
import com.nguyenloi.shop_ecommerce.Class.AllProducts;
import com.nguyenloi.shop_ecommerce.Class.Category;
import com.nguyenloi.shop_ecommerce.Class.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.Class.Manufactures;
import com.nguyenloi.shop_ecommerce.Class.Products;
import com.nguyenloi.shop_ecommerce.R;
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


    ProductsHomeTopSoldAdapter topSoldAdapter;
    ProductsHomeSuggestionAdapter suggestionAdapter;
    ProductsHomeCategoryAdapter categoryAdapter;
    private String category_id, description, image, name, created_at, manu_id, key;
    private int quantity, import_price, sold, price, rating;

    Query querySortBySold, querySortBySuggestion, queryByCategory, querySortBySoldTop4, queryBySuggestion,
           queryByOrderId, queryGetCustomer;;
    List<SlideModel> arrListAds;
    ArrayList<Products> arrProducts;
    ArrayList<Category> arrCategory;
    ArrayList<Manufactures> arrManufacture;
    Products pro;
    Category category;
    Manufactures manufactures;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        setHasOptionsMenu(true);
        setControl(view);

        //Load data search
        arrListAds = new ArrayList<>();
        arrProducts = new ArrayList<>();
        arrCategory = new ArrayList<>();
        arrManufacture = new ArrayList<>();

        //Query
        querySortBySold = FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("sold").limitToFirst(20);
        querySortBySoldTop4 = FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("sold").limitToFirst(4);
        querySortBySuggestion = FirebaseDatabase.getInstance().getReference().child("Products").limitToFirst(10);
        queryByCategory = FirebaseDatabase.getInstance().getReference().child("Categories");
        queryBySuggestion = FirebaseDatabase.getInstance().getReference().
                child("AutocompleteSuggesstion").orderByChild("userId").equalTo(GlobalIdUser.userId);
        queryByOrderId = FirebaseDatabase.getInstance().getReference().
                child("Order").orderByChild("accountID").equalTo(GlobalIdUser.userId);
        queryGetCustomer = FirebaseDatabase.getInstance().getReference().
                child("Customer").orderByChild("id").equalTo(GlobalIdUser.userId);

        //Load data
        loadDataByTopSold();
        loadDataSuggestion();
        loadDataByCategory();
        loadDataAds();
        //Load all products
        loadAllProducts();
        AllProducts.setArrAllProducts(arrProducts);
        //Load all category
        loadAllCategory();
        AllCategory.setArrAllCategory(arrCategory);
        //load all manufactures
        loadAllManufactures();
        AllManufactures.setAllManufactures(arrManufacture);
        //loadOrderUser
        loadOrderUser();
        loadDataUserFromUserId();
        //Tab on keyboard done
        replaceLayoutMenuTop();
        return view;
    }

    private void loadAllManufactures(){
        FirebaseDatabase.getInstance().getReference().child("Manufactures").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot  manu : snapshot.getChildren()) {
                    String idManufactures = manu.getKey();
                    String idCategoryManufactures = manu.getValue(Manufactures.class).getIdCategory();
                    String imageManufactures = manu.getValue(Manufactures.class).getImage();
                    String nameManufactures = manu.getValue(Manufactures.class).getName();

                    manufactures = new Manufactures(idManufactures, idCategoryManufactures, imageManufactures, nameManufactures);
                    arrManufacture.add(manufactures);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadAllProducts() {
        FirebaseDatabase.getInstance().getReference().child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot products : snapshot.getChildren()) {
                    category_id = products.getValue(Products.class).getCategory_id();
                    description = products.getValue(Products.class).getDescription();
                    image = products.getValue(Products.class).getImage();
                    name = products.getValue(Products.class).getName();
                    created_at = products.getValue(Products.class).getCreated_at();
                    manu_id = products.getValue(Products.class).getManu_id();
                    key = products.getKey();
                    quantity = products.getValue(Products.class).getQuantity();
                    import_price = products.getValue(Products.class).getImport_price();
                    sold = products.getValue(Products.class).getSold();
                    price = products.getValue(Products.class).getPrice();
                    rating = products.getValue(Products.class).getRating();
                    pro = new Products(category_id, description, image, name, created_at, manu_id
                            , key, quantity, import_price, sold, price, rating);
                    arrProducts.add(pro);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadAllCategory() {
        FirebaseDatabase.getInstance().getReference().child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ca : snapshot.getChildren()) {
                    String imageCategory = ca.getValue(Category.class).getImage();
                    String nameCategory = ca.getValue(Category.class).getName();
                    String idCategory = ca.getKey();
                    category = new Category(imageCategory,nameCategory,idCategory);
                    arrCategory.add(category);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadOrderUser() {
       queryByOrderId.addValueEventListener(new ValueEventListener() {
            boolean processDone = false;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!processDone && snapshot.exists()) {
                    for (DataSnapshot order : snapshot.getChildren()) {
                        String key = order.getKey();
                        GlobalIdUser.setOrderId(key);
                        processDone = true;
                    }
                } else {
                    processDone = true;
                }
                //Filter data for recycler view
                if (processDone) {
                   if(GlobalIdUser.getOrderId().equals("")){
                       insertNewOrderUser();
                   }
                    processDone = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void insertNewOrderUser(){
        Map<String, Object> map = new HashMap<>();
        map.put("accountID", GlobalIdUser.userId);
        map.put("address", "null");
        map.put("created_at", "null");
        map.put("total", 0);
        map.put("status", "null");
        map.put("transport_fee", "null");
        FirebaseDatabase.getInstance().getReference().child("Order")
                .push().setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

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

    private void loadDataUserFromUserId(){
        queryGetCustomer.addValueEventListener(new ValueEventListener() {
            boolean processDone = false;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!processDone && snapshot.exists()) {
                    for (DataSnapshot customer : snapshot.getChildren()) {
                         String idCustomer = customer.getKey();
                         GlobalIdUser.setCustomerId(idCustomer);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl(View v) {
        rcvHomeTopSold = v.findViewById(R.id.rcvHomeTopSold);
        rcvHomeSuggestion = v.findViewById(R.id.rcvHomeSuggestion);
        rcvHomeCategory = v.findViewById(R.id.rcvHomeCategory);
        imgHomeSlider = v.findViewById(R.id.imgHomeSlider);
    }

    private void replaceLayoutMenuTop() {
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
