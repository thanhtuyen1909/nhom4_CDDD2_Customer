package com.nguyenloi.shop_ecommerce.activites.Other;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.nguyenloi.shop_ecommerce.Class.AllCommentProduct;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.adapters.ProductDetailCommentAdapter;

public class DetailProductComment extends AppCompatActivity {
    RecyclerView rcvAllComment;
    ProductDetailCommentAdapter detailCommentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_comment);
        this.getSupportActionBar().setTitle("Toàn bộ bình luận");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setControl();
        setDataRecyclerView();
    }

    private void setDataRecyclerView() {
        rcvAllComment.setLayoutManager(new LinearLayoutManager(this));
        detailCommentAdapter = new ProductDetailCommentAdapter(DetailProductComment.this, AllCommentProduct.getArrRatingUI());
        rcvAllComment.setAdapter(detailCommentAdapter);
    }

    private void setControl() {
        rcvAllComment=findViewById(R.id.rcvAllComment);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}