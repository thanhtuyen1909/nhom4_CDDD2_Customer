package com.nguyenloi.shop_ecommerce.activites.Other;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nguyenloi.shop_ecommerce.Class.AllProducts;
import com.nguyenloi.shop_ecommerce.Class.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.Class.Products;
import com.nguyenloi.shop_ecommerce.Class.SearchHandle;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.adapters.ProductsFindAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FindProductActivity extends AppCompatActivity {
    RecyclerView rcvFindProducts;
    ProductsFindAdapter findAdapter;
    ImageView imgFindArrowBack;
    TextView tvFindSearchTitle;
    AutoCompleteTextView edtFindAutocompleted;
    String nameProduct;
    private ArrayList<Products> arrProducts;
    Query queryBySuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_product);
        setControl();
        this.getSupportActionBar().hide();

        queryBySuggestion = FirebaseDatabase.getInstance().getReference().
                child("AutocompleteSuggesstion").orderByChild("userId").equalTo(GlobalIdUser.userId);

        loadDataSearch();
        //Get and set name from search main
        nameProduct = SearchHandle.getNameProduct();
        edtFindAutocompleted.setText(nameProduct);

        arrProducts = new ArrayList<>();
        //Load data
        loadDataFilter();

        //loadData search Filter type activity
        if (SearchHandle.typeActivity.equals("Category")) {
            filterDataByCategory();

        } else if (SearchHandle.typeActivity.equals("FindByName")) {
            filterDataByNameProduct();
        }

        //Tab on search
        onClickSearch();
        //Go back page
        imgFindArrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    private void onClickSearch() {
        //Tab on keyboard done
        edtFindAutocompleted.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtFindAutocompleted.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkIsSuggesstion();
                    filterDataByNameProduct();
                    return true;
                }
                return false;
            }
        });

        edtFindAutocompleted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkIsSuggesstion();
                filterDataByNameProduct();
            }
        });
    }

    private void loadDataFilter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FindProductActivity.this, LinearLayoutManager.VERTICAL, false);;
        rcvFindProducts.setLayoutManager(linearLayoutManager);
        setDataChangeRecyclerView();
    }

    private void filterDataByNameProduct() {
        arrProducts.clear();
        if (edtFindAutocompleted.getText().toString().trim().equals("")) {
            arrProducts = AllProducts.getArrAllProducts();
        } else {
            for (int i = 0; i < AllProducts.getArrAllProducts().size(); i++) {
                if (AllProducts.getArrAllProducts().get(i).getName().toLowerCase(Locale.ROOT).trim()
                        .contains(edtFindAutocompleted.getText().toString().toLowerCase(Locale.ROOT).trim())) {
                    arrProducts.add(AllProducts.getArrAllProducts().get(i));
                }
            }
        }
        tvFindSearchTitle.setText("Tìm được: " + arrProducts.size() + " kết quả");
        setDataChangeRecyclerView();
    }

    private void filterDataByCategory() {
        arrProducts.clear();
        for (int i = 0; i < AllProducts.getArrAllProducts().size(); i++) {
            if (AllProducts.getArrAllProducts().get(i).getCategory_id()
                    .contains(SearchHandle.getIdCategory())) {
                arrProducts.add(AllProducts.getArrAllProducts().get(i));
            }
        }
        tvFindSearchTitle.setText("Tìm được: " + arrProducts.size() + " kết quả");
        setDataChangeRecyclerView();
    }

    private void setDataChangeRecyclerView() {
        findAdapter = new ProductsFindAdapter(FindProductActivity.this, arrProducts);
        rcvFindProducts.setAdapter(findAdapter);
    }


    private void setControl() {
        imgFindArrowBack = findViewById(R.id.imgFindArrowBack);
        tvFindSearchTitle = findViewById(R.id.tvFindSearchTitle);
        rcvFindProducts = findViewById(R.id.rcvFindProducts);
        edtFindAutocompleted = findViewById(R.id.edtFindAutocompleted);
    }

    //Load data search by this user
    private void loadDataSearch() {

        final ArrayAdapter<String> autoComplete = new ArrayAdapter<String>(FindProductActivity.this, android.R.layout.simple_list_item_1);
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
        edtFindAutocompleted.setAdapter(autoComplete);
    }


    private void checkIsSuggesstion() {
        FirebaseDatabase.getInstance().getReference().
                child("AutocompleteSuggesstion").addValueEventListener(new ValueEventListener() {
            boolean processDone = false;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot suggestionSnapshot : snapshot.getChildren()) {
                    String suggestion = suggestionSnapshot.child("suggestion").getValue(String.class);
                    String userId = suggestionSnapshot.child("userId").getValue(String.class);
                    if (edtFindAutocompleted.getText().toString().trim().equals(suggestion)
                            && GlobalIdUser.userId.equals(userId)
                            && !processDone) {
                        processDone = true;
                    }
                }
                if (!processDone) {
                    insertToSuggesstionForUser(edtFindAutocompleted.getText().toString());
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


}