package com.nguyenloi.shop_ecommerce.activites.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.Class.UserLogin;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText edtRegisterEmail, edtRegisterPhone, edtRegisterPassword, edtRegisterSubmitPassword;
    Button btnRegisterRegister;
    TextView tvRegisterNotification;
    String passWordParent = "^(?=.*?[A-Z])(?=.*?[a-z]).{6,}$";

    String password, cofirmPassword, email;


    FirebaseDatabase database = FirebaseDatabase.getInstance();;
    DatabaseReference reference = database.getReference();;

    Query filterPhoneNumberUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getSupportActionBar().setTitle("Đăng ký tài khoản");
        setControl();

        tvRegisterNotification.setText("");

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
                //Check lenght password
                if (isValidEmailId(password)) {
                    //Check length phone number
                    if (edtRegisterPhone.getText().toString().length() == 10) {
                        //Check exits phone number
                        filterPhoneNumberUser = reference.child("Account").orderByChild("phone").equalTo(edtRegisterPhone.getText().toString());

                        filterPhoneNumberUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                UserLogin user = snapshot.getValue(UserLogin.class);
                                tvRegisterNotification.setText("Số điện thoại đã tồn tại");
                                try {
                                    if (edtRegisterPhone.getText().toString().equals(user.getPhone())) {
                                        tvRegisterNotification.setText("Go");
                                    }
                                } catch (Exception ex) {
                                    Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
                                    intent.putExtra("phoneNumber", edtRegisterPhone.getText().toString().trim());
                                    intent.putExtra("email", email);
                                    intent.putExtra("password", password);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    } else {
                        tvRegisterNotification.setText("Định dạng số điện thoại không hợp lệ");
                    }
                } else {
                    tvRegisterNotification.setText("Mật khẩu phải lớn hơn 6 ký tự, có ít nhất 1 ký tự thường và 1 kỳ tự in hoa");
                }
            } else {
                tvRegisterNotification.setText("Xác nhận mật khẩu không trùng khớp");
            }
        } else {
            tvRegisterNotification.setText("Không được để rỗng");
        }
    }

    private boolean isValidEmailId(String strPassword) {
        return Pattern.compile(passWordParent).matcher(strPassword).matches();
    }


    private void setControl() {
        edtRegisterEmail = findViewById(R.id.edtRegisterEmail);
        tvRegisterNotification = findViewById(R.id.tvRegisterNotification);
        edtRegisterPhone = findViewById(R.id.edtRegisterPhone);
        edtRegisterPassword = findViewById(R.id.edtRegisterPassword);
        edtRegisterSubmitPassword = findViewById(R.id.edtRegisterSubmitPassword);
        btnRegisterRegister = findViewById(R.id.btnRegisterRegister);
    }
}
