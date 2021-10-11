package com.nguyenloi.shop_ecommerce.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nguyenloi.shop_ecommerce.ChangePasswordDetailActivity;
import com.nguyenloi.shop_ecommerce.R;

public class ChangePasswordActivity extends AppCompatActivity {
    Button btnChangePasswordSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        this.getSupportActionBar().setTitle("Đổi mật khẩu");
        setControl();
        btnChangePasswordSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePasswordActivity.this, ChangePasswordDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setControl() {
        btnChangePasswordSubmit=findViewById(R.id.btnChangePasswordSubmit);
    }
}