package com.nguyenloi.shop_ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyenloi.shop_ecommerce.Class.CommentRating;
import com.nguyenloi.shop_ecommerce.R;

import java.util.ArrayList;

public class CommentListRatingAdapter extends RecyclerView.Adapter<CommentListRatingAdapter.CommentRatingViewHolder>{
    Context mContext;
    ArrayList<CommentRating> list;

    public CommentListRatingAdapter(Context mContext, ArrayList<CommentRating> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public CommentRatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_text_comment_list, parent, false);
        return new CommentListRatingAdapter.CommentRatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentRatingViewHolder holder, int position) {
       CommentRating commentRating = list.get(position);
       holder.tvCardRatingDetailCommentList.setText(" "+commentRating.getComment()+" ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CommentRatingViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCardRatingDetailCommentList;
        private CardView cardRatingDetailCommentList;

        public CommentRatingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardRatingDetailCommentList=itemView.findViewById(R.id.tvCardRatingDetailCommentList);
            cardRatingDetailCommentList=itemView.findViewById(R.id.cardRatingDetailCommentList);
        }
    }
}
