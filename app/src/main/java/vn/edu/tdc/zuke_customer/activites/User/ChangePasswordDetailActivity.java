package vn.edu.tdc.zuke_customer.activites.User;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import vn.edu.tdc.zuke_customer.Class.GlobalIdUser;
import vn.edu.tdc.zuke_customer.R;

import java.util.regex.Pattern;

public class ChangePasswordDetailActivity extends AppCompatActivity {
    TextView tvChangePasswordDetail;
    EditText edtChangePasswordDetailNewPassword, edtChangePasswordDetailConfirmPassword;
    Button btnChangePasswordDetailSubmit;
    String password, confirmPassword;
    String passWordParent = "^(?=.*?[A-Z])(?=.*?[a-z]).{6,}$";
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_detail);
        this.getSupportActionBar().setTitle("Đổi mật khẩu");
        setControl();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        btnChangePasswordDetailSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = edtChangePasswordDetailNewPassword.getText().toString();
                confirmPassword = edtChangePasswordDetailConfirmPassword.getText().toString();
                checkChangePasswordUser();

            }
        });
    }

    private void checkChangePasswordUser(){
        if (!password.equals("") && !confirmPassword.equals("")) {
            if (isValidEmailId(password)) {
                if (password.equals(confirmPassword)) {
                    changePassword(password);
                    GlobalIdUser.setPassword(password);
                    finish();
                    Toast.makeText(ChangePasswordDetailActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                } else {
                    tvChangePasswordDetail.setText("Xác nhận mật khẩu không trùng khớp");
                }
            } else {
                tvChangePasswordDetail.setText("Mật khẩu phải lớn hơn 6 ký tự, có ít nhất 1 ký tự thường và 1 kỳ tự in hoa");
            }
        } else {
            tvChangePasswordDetail.setText("Không được để trống");
        }
    }

    private void changePassword(String nPassword) {
            reference.child("Account").child(GlobalIdUser.userId).child("password").setValue(nPassword);
    }

    private boolean isValidEmailId(String strPassword) {
        return Pattern.compile(passWordParent).matcher(strPassword).matches();
    }

    private void setControl() {
        tvChangePasswordDetail = findViewById(R.id.tvChangePasswordDetail);
        edtChangePasswordDetailConfirmPassword = findViewById(R.id.edtChangePasswordDetailConfirmPassword);
        edtChangePasswordDetailNewPassword = findViewById(R.id.edtChangePasswordDetailNewPassword);
        btnChangePasswordDetailSubmit = findViewById(R.id.btnChangePasswordDetailSubmit);
    }
}