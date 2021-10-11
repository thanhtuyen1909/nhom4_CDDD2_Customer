package com.nguyenloi.shop_ecommerce.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nguyenloi.shop_ecommerce.R;

public class ForgetPasswordActivity extends AppCompatActivity {
    Button btnForgetSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        this.getSupportActionBar().setTitle("Quên mật khẩu");
        setControl();
        btnForgetSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordActivity.this, OtpActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setControl() {
        btnForgetSubmit=findViewById(R.id.btnForgetSubmit);
    }
}