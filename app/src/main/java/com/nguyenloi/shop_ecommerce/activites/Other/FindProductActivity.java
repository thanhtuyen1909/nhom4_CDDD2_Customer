package com.nguyenloi.shop_ecommerce.activites.Other;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nguyenloi.shop_ecommerce.Class.AllManufactures;
import com.nguyenloi.shop_ecommerce.Class.AllProducts;
import com.nguyenloi.shop_ecommerce.Class.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.Class.Products;
import com.nguyenloi.shop_ecommerce.Class.SearchHandle;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.adapters.ProductsFindAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    ImageView imgFindFilter;
    RangeSlider sRangePriceFilter;
    RatingBar ratingFilter;
    Spinner spFilterManufactures;
    Button btnFilterRefresh, btnFilterSubmit;
    List<String> arrNameManufactures;
    DrawerLayout navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_product);
        setControl();
        this.getSupportActionBar().hide();

        queryBySuggestion = FirebaseDatabase.getInstance().getReference().
                child("AutocompleteSuggesstion").orderByChild("userId").equalTo(GlobalIdUser.userId);

        //Add data spinner
        arrNameManufactures = new ArrayList<>();
        loadDataSpinnerManufactures();
        //SetOnClickFrom NavigationDrawable
        tabOnInDrawable();

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

    private void tabOnInDrawable() {
        imgFindFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 navDrawer = findViewById(R.id.activity_main_drawer);
                if (!navDrawer.isDrawerOpen(Gravity.RIGHT)) navDrawer.openDrawer(Gravity.RIGHT);
                else navDrawer.closeDrawer(Gravity.LEFT);
            }
        });

        btnFilterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrProducts.clear();
                List<Float> data1 = sRangePriceFilter.getValues();
               int  valueGetMin = Math.round(data1.get(0));
               int  valueGetMax = Math.round(data1.get(data1.size()-1));
               int userRating = (int)Math.round(ratingFilter.getRating());
               String getTextSpinner = spFilterManufactures.getSelectedItem().toString();
               for(int i=0;i<AllProducts.getArrAllProducts().size();i++){
                   //Filter by start
                   if(AllProducts.getArrAllProducts().get(i).getRating()==userRating){
                       int iPrice =AllProducts.getArrAllProducts().get(i).getPrice();
                      //Filter by price
                       if(iPrice>=valueGetMin&&iPrice<=valueGetMax){
                          //Filter by manuFactures
                           for(int j=0; j<AllManufactures.getAllManufactures().size();j++){
                               if(AllManufactures.getAllManufactures().get(j).getName().equals(getTextSpinner)
                               &&AllManufactures.getAllManufactures().get(j).getIdManufactures()
                                       .equals(AllProducts.getArrAllProducts().get(i).getManu_id())){
                                   arrProducts.add(AllProducts.getArrAllProducts().get(i));
                               }
                           }
                       }
                   }
               }
                setDataChangeRecyclerView();
            }
        });

          btnFilterRefresh.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  ratingFilter.setRating(Float.parseFloat("0.0"));
                  sRangePriceFilter.setValues(0.0f,0.0f);
                  spFilterManufactures.setSelection(0);
              }
          });

    }

    private void loadDataSpinnerManufactures() {
        for (int i = 0; i < AllManufactures.getAllManufactures().size(); i++) {
            arrNameManufactures.add(AllManufactures.getAllManufactures().get(i).getName());
        }

        //setType spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrNameManufactures);
        adapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        spFilterManufactures.setAdapter(adapter);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FindProductActivity.this, LinearLayoutManager.VERTICAL, false);
        ;
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
        imgFindFilter = findViewById(R.id.imgFindFilter);
        imgFindArrowBack = findViewById(R.id.imgFindArrowBack);
        tvFindSearchTitle = findViewById(R.id.tvFindSearchTitle);
        rcvFindProducts = findViewById(R.id.rcvFindProducts);
        edtFindAutocompleted = findViewById(R.id.edtFindAutocompleted);
        btnFilterRefresh = findViewById(R.id.btnFilterRefresh);
        btnFilterSubmit = findViewById(R.id.btnFilterSubmit);
        spFilterManufactures = findViewById(R.id.spFilterManufactures);
        sRangePriceFilter = findViewById(R.id.sRangePriceFilter);
        ratingFilter = findViewById(R.id.ratingFilter);
    }

    //Load data search by this user
    private void loadDataSearch() {
        final ArrayAdapter<String> autoComplete = new ArrayAdapter<String>(FindProductActivity.this, android.R.layout.simple_list_item_1);
        queryBySuggestion.addValueEventListener(new ValueEventListener() {
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