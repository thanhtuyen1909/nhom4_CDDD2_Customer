package com.nguyenloi.shop_ecommerce.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nguyenloi.shop_ecommerce.Class.AllProducts;
import com.nguyenloi.shop_ecommerce.Class.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.Class.SearchHandle;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.activites.Cart.CartActivity;
import com.nguyenloi.shop_ecommerce.activites.Other.FindProductActivity;

import java.util.HashMap;
import java.util.Map;

public class SearchItemFragment extends Fragment {
    AutoCompleteTextView edtAutoCompleted;
    ImageView imgIconHomeShop, imgIconHomeNotification;
    Query queryBySuggestion;

    public SearchItemFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_item, container, false);
        setControl(view);
        queryBySuggestion = FirebaseDatabase.getInstance().getReference().
                child("AutocompleteSuggesstion").orderByChild("userId").equalTo(GlobalIdUser.userId);

        loadDataSearch();

        onClickSearch();

        //Tab on icon notification
        imgIconHomeNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getContext(), CartActivity.class);
               startActivity(intent);
            }
        });

        return view;
    }
     private void onClickSearch(){
         //Tab on keyboard done
         edtAutoCompleted.setImeOptions(EditorInfo.IME_ACTION_DONE);
         edtAutoCompleted.setOnEditorActionListener(new TextView.OnEditorActionListener() {
             @Override
             public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                 if (actionId == EditorInfo.IME_ACTION_DONE) {
                     checkIsSuggesstion();
                     goToPageSearch();
                     return true;
                 }
                 return false;
             }
         });

         edtAutoCompleted.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 checkIsSuggesstion();
                 goToPageSearch();
             }
         });
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

    private void goToPageSearch(){
        String nameProduct = edtAutoCompleted.getText().toString();
        SearchHandle.setNameProduct(nameProduct);
        SearchHandle.setTypeActivity("FindByName");
        Intent i = new Intent(getContext(), FindProductActivity.class);
        startActivity(i);
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
                            && GlobalIdUser.userId.equals(userId)
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

    private void setControl(View v) {
        edtAutoCompleted = v.findViewById(R.id.idAutoCompleted);
        imgIconHomeNotification = v.findViewById(R.id.imgIconHomeNotification);
        imgIconHomeShop = v.findViewById(R.id.imgIconHomeShop);
    }
}