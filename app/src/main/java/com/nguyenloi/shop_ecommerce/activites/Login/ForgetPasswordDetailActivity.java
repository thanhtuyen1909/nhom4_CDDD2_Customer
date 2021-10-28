package com.nguyenloi.shop_ecommerce.activites.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nguyenloi.shop_ecommerce.R;

import java.util.regex.Pattern;

public class ForgetPasswordDetailActivity extends AppCompatActivity {
    TextView tvForgetPasswordDetail;
    EditText edtForgetPasswordDetailPassword, edtForgetPasswordDetailConfirm;
    Button btnForgetPasswordDetailSumit;
    String password, confirmPassword;
    String passWordParent = "^(?=.*?[A-Z])(?=.*?[a-z]).{6,}$";
    FirebaseDatabase database;
    DatabaseReference reference;

    Intent intentData;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_detail);
        this.getSupportActionBar().setTitle("Quên mật khẩu");
        setControl();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        intentData=getIntent();
        userId=intentData.getStringExtra("userIdNotLogin");

        btnForgetPasswordDetailSumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = edtForgetPasswordDetailPassword.getText().toString();
                confirmPassword = edtForgetPasswordDetailConfirm.getText().toString();
                checkChangePasswordUser();

            }
        });
    }
    private void checkChangePasswordUser(){
        if (!password.equals("") && !confirmPassword.equals("")) {
            if (isValidEmailId(password)) {
                if (password.equals(confirmPassword)) {
                    changePassword(password);
                    Toast.makeText(ForgetPasswordDetailActivity.this, "Đổi mât khẩu thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    tvForgetPasswordDetail.setText("Xác nhận mật khẩu không trùng khớp");
                }
            } else {
                tvForgetPasswordDetail.setText("Mật khẩu phải lớn hơn 6 ký tự, có ít nhất 1 ký tự thường và 1 kỳ tự in hoa");
            }
        } else {
            tvForgetPasswordDetail.setText("Không được để trống");
        }
    }

    private void changePassword(String nPassword) {
        reference.child("Account").child(userId).child("password").setValue(nPassword);
    }

    private boolean isValidEmailId(String strPassword) {
        return Pattern.compile(passWordParent).matcher(strPassword).matches();
    }

    private void setControl() {
        tvForgetPasswordDetail = findViewById(R.id.tvForgetPasswordDetail);
        edtForgetPasswordDetailConfirm = findViewById(R.id.edtForgetPasswordDetailConfirm);
        edtForgetPasswordDetailPassword = findViewById(R.id.edtForgetPasswordDetailPassword);
        btnForgetPasswordDetailSumit = findViewById(R.id.btnForgetPasswordDetailSumit);
    }

}