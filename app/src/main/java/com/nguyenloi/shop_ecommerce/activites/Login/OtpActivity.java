package com.nguyenloi.shop_ecommerce.activites.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nguyenloi.shop_ecommerce.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    Button btnOtpSubmit;
    EditText edtOtp;
    TextView tvOtp;
    String codeSent, userId;

    String password, email, phone;

    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    Intent getActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        this.getSupportActionBar().setTitle("Quên mật khẩu");
        setControl();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        getActivity = getIntent();

        if (getIntent().hasExtra("userIdNotLogin")) {
            String phoneNumber = "+84" + getIntent().getStringExtra("phoneNumber");
            userId = getIntent().getStringExtra("userIdNotLogin");
            Toast.makeText(OtpActivity.this, "key "+userId, Toast.LENGTH_SHORT).show();
            phone = getIntent().getStringExtra("phoneNumber");
            tvOtp.setText("Đã có mã OTP gửi đến số điện thoại: " + phone + " bạn hãy xác nhận thông tin");
            sendSms(phoneNumber);
        }
        //RegisterActivity
        else {
            String phoneNumber = "+84" + getIntent().getStringExtra("phoneNumber");
            email = getIntent().getStringExtra("email");
            password = getIntent().getStringExtra("password");
            phone = getIntent().getStringExtra("phoneNumber");

            tvOtp.setText("Đã có mã OTP gửi đến số điện thoại: " + phone + " bạn hãy xác nhận thông tin");

            sendSms(phoneNumber);

        }
        btnOtpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!edtOtp.getText().toString().trim().equals("")){
                   signWithPhoneCode();
               }else{
                   tvOtp.setText("Không được để trống");
               }
            }
        });

    }

    private void sendSms(String strPhone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(strPhone, 60,
                TimeUnit.SECONDS,
                OtpActivity.this, mCallbacks);
    }

    //Insert To Firebase
    private void insertToFirebase() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", email);
        map.put("password", password);
        map.put("image", "null");
        map.put("status", "unlock");
        map.put("role_id", 1);
        map.put("phone", phone);

        reference.child("Account").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OtpActivity.this, "Lỗi: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void signWithPhoneCode() {
        String userEnterCode = edtOtp.getText().toString().trim();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, userEnterCode);
        signInWithPhoneAuthCreadential(credential);
    }


    public void signInWithPhoneAuthCreadential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Go to page ChangePassword of page forgetPassword
                            if (getIntent().hasExtra("userIdNotLogin")) {
                                goToPageChangePassword();
                            }
                            //Register page
                            else {
                                Toast.makeText(OtpActivity.this, "Bạn đã đăng ký thành công", Toast.LENGTH_SHORT).show();
                                insertToFirebase();
                            }
                            finish();
                        } else {
                            Toast.makeText(OtpActivity.this, "Mã OTP không hợp lệ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void goToPageChangePassword() {
        Intent i = new Intent(OtpActivity.this, ForgetPasswordDetailActivity.class);
        i.putExtra("userIdNotLogin", userId);
        startActivity(i);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeSent = s;
                }
            };


    private void setControl() {
        edtOtp = findViewById(R.id.edtOtp);
        btnOtpSubmit = findViewById(R.id.btnOtpSubmit);
        tvOtp = findViewById(R.id.tvOtp);
    }
}