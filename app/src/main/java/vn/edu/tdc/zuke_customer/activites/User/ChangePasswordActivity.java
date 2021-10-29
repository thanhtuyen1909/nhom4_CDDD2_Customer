package vn.edu.tdc.zuke_customer.activites.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.tdc.zuke_customer.R;

public class ChangePasswordActivity extends AppCompatActivity {
    Button btnChangePasswordSubmit;
    EditText edtChangePasswordCurrent;
    TextView tvChangePasswordCurrent;
    String passwordUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        this.getSupportActionBar().setTitle("Đổi mật khẩu");
        setControl();
        Intent nIntent = getIntent();
        passwordUser = nIntent.getStringExtra("password");
        btnChangePasswordSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtChangePasswordCurrent.getText().toString().trim().equals("")) {
                    if (edtChangePasswordCurrent.getText().toString().equals(passwordUser)) {
                        Intent intent = new Intent(ChangePasswordActivity.this, ChangePasswordDetailActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        tvChangePasswordCurrent.setText("Mật khẩu không chính xác");
                    }
                }else{
                    tvChangePasswordCurrent.setText("Không được để trống");
                }

            }
        });
    }


    private void setControl() {
        btnChangePasswordSubmit = findViewById(R.id.btnChangePasswordSubmit);
        edtChangePasswordCurrent = findViewById(R.id.edtChangePasswordCurrent);
        tvChangePasswordCurrent = findViewById(R.id.tvChangePasswordCurrent);
    }
}