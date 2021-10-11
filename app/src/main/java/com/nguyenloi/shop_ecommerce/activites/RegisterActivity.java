package com.nguyenloi.shop_ecommerce.activites;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nguyenloi.shop_ecommerce.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getSupportActionBar().setTitle("Đăng ký tài khoản");
    }
}
