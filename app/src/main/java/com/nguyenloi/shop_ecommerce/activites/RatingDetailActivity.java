package com.nguyenloi.shop_ecommerce.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.nguyenloi.shop_ecommerce.Class.CommentRating;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.Class.TheRating;
import com.nguyenloi.shop_ecommerce.adapters.CommentListRatingAdapter;
import com.nguyenloi.shop_ecommerce.adapters.ProductsFavoriteAdapter;

import java.util.ArrayList;

public class RatingDetailActivity extends AppCompatActivity {
    RecyclerView rcvRatingDetailComment,rcvRatingDetailCommentList;
    Button btnRatingDetail;
    CommentListRatingAdapter adapter;
    ArrayList<CommentRating> arrComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_detail);
        setControl();
        this.getSupportActionBar().setTitle("Đánh giá sản phẩm");

        arrComment = new ArrayList<>();
        addDataDefault();
        setRecyclerViewCommentList();
    }

    private void setRecyclerViewCommentList(){
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(RatingDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rcvRatingDetailCommentList.setLayoutManager(layoutManager);
        rcvRatingDetailCommentList.setAdapter(adapter);
        adapter = new CommentListRatingAdapter(RatingDetailActivity.this, arrComment);
        rcvRatingDetailCommentList.setAdapter(adapter);
    }

    private void addDataDefault(){
        arrComment.add(new CommentRating("Sản phẩm này thật tuyệt vời"));
        arrComment.add(new CommentRating("Giao hàng nhanh"));
        arrComment.add(new CommentRating("Sau mình sẽ ủng hộ cho shop tiếp"));
        arrComment.add(new CommentRating("Tuyệt vời"));
        arrComment.add(new CommentRating("Hàng chất lượng cao"));
        arrComment.add(new CommentRating("Sản phẩm giống hình minh hoạ"));
        arrComment.add(new CommentRating("Shop tư vấn nhiệt tình"));
        arrComment.add(new CommentRating("Sản phẩm đúng như là đã mô tả"));
        arrComment.add(new CommentRating("Tốt"));
        arrComment.add(new CommentRating("Cho shop 5 sao"));
    }

    private void setControl() {
        rcvRatingDetailComment=findViewById(R.id.rcvRatingDetailComment);
        rcvRatingDetailCommentList=findViewById(R.id.rcvRatingDetailCommentList);
        btnRatingDetail=findViewById(R.id.btnRatingDetail);

    }


}