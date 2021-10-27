package com.nguyenloi.shop_ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyenloi.shop_ecommerce.Class.RatingForUI;
import com.nguyenloi.shop_ecommerce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetailCommentAdapter extends RecyclerView.Adapter<ProductDetailCommentAdapter.DetailCommentViewHolder>{
    Context mContext;
    ArrayList<RatingForUI> arrRating;

    public ProductDetailCommentAdapter(Context mContext, ArrayList<RatingForUI> arrRating) {
        this.mContext = mContext;
        this.arrRating = arrRating;
    }

    @NonNull
    @Override
    public DetailCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_viewcomment_product, parent, false);
        return new ProductDetailCommentAdapter.DetailCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailCommentViewHolder holder, int position) {
       RatingForUI rating = arrRating.get(position);
        Picasso.get().load(rating.getImageUser()).into(holder.imgProductDetailRating);
        holder.tvProductDetailRatingDescription.setText(rating.getCommentDescription());
        holder.tvProductDetailRatingName.setText(rating.getNameUser());
        holder.ratingBarProductDetailRating.setRating(Float.parseFloat(rating.getNumStar()+".0"));
    }

    @Override
    public int getItemCount() {
        return arrRating.size();
    }

    public class DetailCommentViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgProductDetailRating;
        private RatingBar ratingBarProductDetailRating;
        private TextView tvProductDetailRatingName,tvProductDetailRatingDescription;

        public DetailCommentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductDetailRating = itemView.findViewById(R.id.imgProductDetailRating);
            ratingBarProductDetailRating = itemView.findViewById(R.id.ratingBarProductDetailRating);
            tvProductDetailRatingDescription= itemView.findViewById(R.id.tvProductDetailRatingDescription);
            tvProductDetailRatingName = itemView.findViewById(R.id.tvProductDetailRatingName);
        }
    }
}
