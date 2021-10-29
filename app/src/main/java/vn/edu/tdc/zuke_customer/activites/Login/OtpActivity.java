package vn.edu.tdc.zuke_customer.activites.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import vn.edu.tdc.zuke_customer.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
        Map<String, Object> mapAccount = new HashMap<>();
        mapAccount.put("password", password);
        mapAccount.put("status", "unlock");
        mapAccount.put("role_id", 1);
        mapAccount.put("username", phone);

        Map<String, Object> mapCustomer = new HashMap<>();
        String getNow = getDateTime();
        mapCustomer.put("created_at",getNow );
        mapCustomer.put("dob","null");
        mapCustomer.put("accountID", "abc"+phone);
        mapCustomer.put("image", "null");
        mapCustomer.put("name", email);
        mapCustomer.put("status","red");
        mapCustomer.put("type_id", "Type1");

        reference.child("Account").child("abc"+phone)
                .setValue(mapAccount)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                      reference.child("Customer").push()
                              .setValue(mapCustomer);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(OtpActivity.this, "Lỗi: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String getDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return simpleDateFormat.format(date);
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
        finish();
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