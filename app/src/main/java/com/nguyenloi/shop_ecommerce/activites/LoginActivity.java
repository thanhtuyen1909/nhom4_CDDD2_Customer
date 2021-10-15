package com.nguyenloi.shop_ecommerce.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nguyenloi.shop_ecommerce.R;

public class LoginActivity extends AppCompatActivity {
    Button  btnLoginLogin;
    TextView btnLoginRegister, btnLoginForgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControl();
        this.getSupportActionBar().setTitle("Đăng nhập");
        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, BottomNavigationUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLoginForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        btnLoginRegister = findViewById(R.id.btnLoginRegister);
        btnLoginForgetPassword = findViewById(R.id.btnLoginForgetPassword);
        btnLoginLogin=findViewById(R.id.btnLoginLogin);
    }

}