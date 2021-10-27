package com.nguyenloi.shop_ecommerce.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.nguyenloi.shop_ecommerce.R;

import java.util.ArrayList;

public class RatingDetailActivity extends AppCompatActivity {
    RecyclerView rcvRatingDetail;
    Button btnRatingDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_detail);
        setControl();
        this.getSupportActionBar().setTitle("Đánh giá sản phẩm");

    }





    private void setControl() {
        rcvRatingDetail=findViewById(R.id.rcvRatingDetail);
        btnRatingDetail=findViewById(R.id.btnRatingDetail);

    }


}