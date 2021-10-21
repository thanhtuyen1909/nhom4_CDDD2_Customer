package com.nguyenloi.shop_ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nguyenloi.shop_ecommerce.Class.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.activites.Other.FindProductActivity;

import java.util.HashMap;
import java.util.Map;

public class SearchItemActivity extends AppCompatActivity {
    AutoCompleteTextView edtAutoCompleted;
    ImageView imgIconHomeShop, imgIconHomeNotification;
    Query queryBySuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);
        setControl();
        queryBySuggestion = FirebaseDatabase.getInstance().getReference().
                child("AutocompleteSuggesstion").orderByChild("userId").equalTo(GlobalIdUser.userId);

        loadDataSearch();

        //Tab on keyboard done
        edtAutoCompleted.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtAutoCompleted.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkIsSuggesstion();
                    Intent i = new Intent(SearchItemActivity.this, FindProductActivity.class);
                    i.putExtra("nameProduct", edtAutoCompleted.getText().toString());
                    Toast.makeText(SearchItemActivity.this, "send " + edtAutoCompleted.getText().toString(), Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(SearchItemActivity.this, FindProductActivity.class);
                startActivity(intent);
            }
        });
    }
    //Load data search by this user
    private void loadDataSearch() {

        final ArrayAdapter<String> autoComplete = new ArrayAdapter<String>(SearchItemActivity.this, android.R.layout.simple_list_item_1);
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

    private void setControl() {
        edtAutoCompleted = findViewById(R.id.idAutoCompleted);
        imgIconHomeNotification = findViewById(R.id.imgIconHomeNotification);
        imgIconHomeShop = findViewById(R.id.imgIconHomeShop);
    }
}