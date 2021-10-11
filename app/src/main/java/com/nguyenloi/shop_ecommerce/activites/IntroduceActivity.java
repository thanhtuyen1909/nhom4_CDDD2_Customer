package com.nguyenloi.shop_ecommerce.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nguyenloi.shop_ecommerce.R;

public class IntroduceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        this.getSupportActionBar().setTitle("Giới thiệu");
    }
}