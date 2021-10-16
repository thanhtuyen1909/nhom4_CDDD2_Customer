package com.nguyenloi.shop_ecommerce.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nguyenloi.shop_ecommerce.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText edtRegisterEmail, edtRegisterPhone, edtRegisterPassword, edtRegisterSubmitPassword;
    Button btnRegisterRegister;

    String password, cofirmPassword, email;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getSupportActionBar().setTitle("Đăng ký tài khoản");
        setControl();


        btnRegisterRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = edtRegisterPassword.getText().toString();
                cofirmPassword = edtRegisterSubmitPassword.getText().toString();
                email = edtRegisterEmail.getText().toString().trim();

                checkData();
            }
        });
    }

    private void checkData() {

        //Check null
        if (!password.equals("") && !email.equals("") && !cofirmPassword.equals("")
                && !edtRegisterPhone.getText().toString().equals("")) {
            //Check cofirmPassword
            if (password.equals(cofirmPassword)) {
                //Check valid email
                if(email.matches(emailPattern)){
                    //Check lenght password
                    if(password.length()>=6){
                        //Check length phone number
                        if(edtRegisterPhone.getText().toString().length()==10){
                             Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
                             intent.putExtra("phoneNumber",edtRegisterPhone.getText().toString().trim());
                             intent.putExtra("email", email);
                             intent.putExtra("password",password);
                             startActivity(intent);
                             finish();
                        }else{
                            Toast.makeText(RegisterActivity.this, "Định dạng số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterActivity.this, "Mật khẩu phải lớn hơn 6 ký tự", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RegisterActivity.this, "Định dạng email không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegisterActivity.this, "Xác nhận mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "Không được để rỗng", Toast.LENGTH_SHORT).show();
        }
    }


    private void setControl() {
        edtRegisterEmail = findViewById(R.id.edtRegisterEmail);
        edtRegisterPhone = findViewById(R.id.edtRegisterPhone);
        edtRegisterPassword = findViewById(R.id.edtRegisterPassword);
        edtRegisterSubmitPassword = findViewById(R.id.edtRegisterSubmitPassword);
        btnRegisterRegister = findViewById(R.id.btnRegisterRegister);
    }
}
