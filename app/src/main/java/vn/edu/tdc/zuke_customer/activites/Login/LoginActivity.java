package vn.edu.tdc.zuke_customer.activites.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import vn.edu.tdc.zuke_customer.Class.GlobalIdUser;
import vn.edu.tdc.zuke_customer.Class.UserLogin;
import vn.edu.tdc.zuke_customer.R;
import vn.edu.tdc.zuke_customer.activites.Other.BottomNavigationUserActivity;

public class LoginActivity extends AppCompatActivity {
    Button btnLoginLogin;
    TextView btnLoginRegister, btnLoginForgetPassword;
    EditText edtLoginPhoneNum, edtLoginPassword;

    FirebaseDatabase database;
    DatabaseReference reference;
    Query filterDataAccountUser;

    String edtPhone, edtPassword;
    String userId;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControl();


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Account");

        //Load data
        filterDataAccountUser = reference.orderByChild("role_id").equalTo(1);

        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPhone = edtLoginPhoneNum.getText().toString();
                edtPassword = edtLoginPassword.getText().toString();

                if (!edtPhone.equals("") && !edtPassword.equals("")) {
                    loginWithFirebase();
                } else {
                    showNotification("Không được để trống");
                }
            }
        });
        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLoginForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }


    private void showNotification(String str) {
        Snackbar snackbar = Snackbar
                .make(view, str, Snackbar.LENGTH_LONG)
                .setAction("Đóng", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Code khi bấm vào nút thư lại ở đây
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    private void loginWithFirebase() {
        filterDataAccountUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot user : snapshot.getChildren()) {
                        userId = user.getKey();
                        String username = user.getValue(UserLogin.class).getUsername();
                        String password = user.getValue(UserLogin.class).getPassword();
                        if (edtPhone.equals(username) && edtPassword.equals(password)) {
                            GlobalIdUser globalIdUser = new GlobalIdUser();
                            globalIdUser.setUserId(userId);
                            globalIdUser.setPhoneNumber(username);
                            globalIdUser.setPassword(password);
                            Intent intent = new Intent(LoginActivity.this, BottomNavigationUserActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            showNotification("Tài khoản hoặc mật khẩu không chính xác");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setControl() {
        btnLoginRegister = findViewById(R.id.btnLoginRegister);
        btnLoginForgetPassword = findViewById(R.id.btnLoginForgetPassword);
        btnLoginLogin = findViewById(R.id.btnLoginLogin);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        edtLoginPhoneNum = findViewById(R.id.edtLoginPhoneNum);
        view = findViewById(R.id.loginSnackBar);
    }

}