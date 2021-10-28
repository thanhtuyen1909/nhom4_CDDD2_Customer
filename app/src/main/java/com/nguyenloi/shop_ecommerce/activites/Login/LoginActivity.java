package com.nguyenloi.shop_ecommerce.activites.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.nguyenloi.shop_ecommerce.Class.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.Class.UserLogin;
import com.nguyenloi.shop_ecommerce.activites.Other.BottomNavigationUserActivity;

public class LoginActivity extends AppCompatActivity {
    Button btnLoginLogin;
    TextView btnLoginRegister, btnLoginForgetPassword;
    EditText edtLoginPhoneNum, edtLoginPassword;

    FirebaseDatabase database;
    DatabaseReference reference;
    Query filterDataAccountUser;

    String phone, password;
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
                phone = edtLoginPhoneNum.getText().toString();
                password = edtLoginPassword.getText().toString();

               if(!phone.equals("")&&!password.equals("")){
                   loginWithFirebase();
               }else{
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


   private void showNotification(String str){
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

   private void loginWithFirebase(){
       filterDataAccountUser.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               UserLogin user = snapshot.getValue(UserLogin.class);
               if (phone.equals(user.getUsername()) && password.equals(user.getPassword())) {
                   userId = snapshot.getKey();
                   Intent intent = new Intent(LoginActivity.this, BottomNavigationUserActivity.class);
                   GlobalIdUser globalIdUser=new GlobalIdUser();
                   globalIdUser.setUserId(userId);
                   globalIdUser.setPhoneNumber(user.getUsername());
                   globalIdUser.setPassword(user.getPassword());
                   startActivity(intent);
                   finish();
               }
               showNotification("Tài khoản hoặc mật khẩu không chính xác");
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