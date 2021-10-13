package com.nguyenloi.shop_ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.nguyenloi.shop_ecommerce.adapters.TheRatingAdapter;

public class RatingDetailActivity extends AppCompatActivity {
    ListView lvBillRatingDetail;
    TheRatingAdapter theRatingAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_detail);
        setControl();
        addData();
    }

    private void addData() {
        theRatingAdapter.add(new TheRating(R.drawable.lap3,getString(R.string.commentRating1),"Laptop ASUS","Đen"));
        theRatingAdapter.add(new TheRating(R.drawable.lap6,getString(R.string.commentRating2),"Laptop Macbook","Trắng"));
        theRatingAdapter.add(new TheRating(R.drawable.lap2,getString(R.string.commentRating3),"Laptop Dell","Xanh"));
    }

    private void setControl() {
        lvBillRatingDetail=findViewById(R.id.lvBillRatingDetail);
        theRatingAdapter = new TheRatingAdapter(RatingDetailActivity.this, R.layout.item_rating_detail);
        lvBillRatingDetail.setAdapter(theRatingAdapter);


    }
}