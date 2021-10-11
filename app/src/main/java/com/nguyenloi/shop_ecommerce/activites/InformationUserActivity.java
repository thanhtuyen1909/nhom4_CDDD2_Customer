package com.nguyenloi.shop_ecommerce.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nguyenloi.shop_ecommerce.R;

public class InformationUserActivity extends AppCompatActivity {
    Button btnInformationChangePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_user);
        this.getSupportActionBar().setTitle("Thiết lập tài khoản");
        setControl();
        btnInformationChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationUserActivity.this, ChangePasswordActivity.class);
                startActivity(intent);

            }
        });
    }

    private void setControl() {
        btnInformationChangePassword=findViewById(R.id.btnInformationChangePassword);
    }
}