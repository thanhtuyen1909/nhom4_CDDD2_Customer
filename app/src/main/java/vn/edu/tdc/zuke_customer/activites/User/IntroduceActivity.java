package vn.edu.tdc.zuke_customer.activites.User;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.tdc.zuke_customer.R;

public class IntroduceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        this.getSupportActionBar().setTitle("Giới thiệu");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}