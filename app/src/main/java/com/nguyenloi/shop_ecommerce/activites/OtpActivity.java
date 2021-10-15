package com.nguyenloi.shop_ecommerce.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nguyenloi.shop_ecommerce.R;

public class OtpActivity extends AppCompatActivity {
    Button btnOtpSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        this.getSupportActionBar().setTitle("Quên mật khẩu");
        setControl();
        btnOtpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpActivity.this, NewPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        btnOtpSubmit=findViewById(R.id.btnOtpSubmit);
    }
}