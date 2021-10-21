package com.nguyenloi.shop_ecommerce.activites.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nguyenloi.shop_ecommerce.R;

public class ForgetPasswordActivity extends AppCompatActivity {
    Button btnForgetSubmit;
    EditText edtForgetPhoneNumber;
    String phoneNumber;
    TextView tvForgetPassword;
    FirebaseDatabase database;
    DatabaseReference reference;
    Query filterPhoneNumberUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        this.getSupportActionBar().setTitle("Quên mật khẩu");
        setControl();

        database = FirebaseDatabase.getInstance();
        ;
        reference = database.getReference();
        ;

        btnForgetSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = edtForgetPhoneNumber.getText().toString();
                checkPhoneNumber(phoneNumber);
                tvForgetPassword.setText("Số điện thoại đã tồn tại");
            }
        });


    }

    private void checkPhoneNumber(String strPhone) {
        if (!strPhone.equals("")) {
            //valid phone number
            if (strPhone.length() == 10) {
                //Phone number have in database
                filterPhoneNumberUser = reference.child("Account").orderByChild("phone").equalTo(strPhone);
                filterPhoneNumberUser.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String key = snapshot.getKey();
                        Intent intent = new Intent(ForgetPasswordActivity.this, OtpActivity.class);
                        intent.putExtra("phoneNumber", phoneNumber);
                        intent.putExtra("userIdNotLogin", key);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                tvForgetPassword.setText("Số điện thoại không tồn tại");

            } else {
                tvForgetPassword.setText("Định dạng số điện thoại khong hợp lệ");
            }
        } else {
            tvForgetPassword.setText("Bạn không được để trống");
        }
    }

    private void setControl() {
        edtForgetPhoneNumber = findViewById(R.id.edtForgetPhoneNumber);
        btnForgetSubmit = findViewById(R.id.btnForgetSubmit);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);
    }
}