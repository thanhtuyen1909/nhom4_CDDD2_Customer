package vn.edu.tdc.zuke_customer.activites.Other;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.tdc.zuke_customer.Class.AllCommentProduct;
import vn.edu.tdc.zuke_customer.R;
import vn.edu.tdc.zuke_customer.adapters.ProductDetailCommentAdapter;

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