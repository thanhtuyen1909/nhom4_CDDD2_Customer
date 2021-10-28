package com.nguyenloi.shop_ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nguyenloi.shop_ecommerce.Class.Products;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.activites.Other.DetailProductActivity;
import com.squareup.picasso.Picasso;

public class ProductsHomeTopSoldAdapter extends FirebaseRecyclerAdapter<Products, ProductsHomeTopSoldAdapter.HomeTopSoldViewHolder> {
    Context context;

    public ProductsHomeTopSoldAdapter(@NonNull FirebaseRecyclerOptions<Products> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeTopSoldViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Products model) {
        holder.tvCardHomeTopSoldPrice.setText(model.getPrice() + "đ");
        if(model.getName().length()<12){
            holder.tvCardHomeTopSoldName.setText(model.getName());
        }else{
            String strName = model.getName().substring(0,11)+"..";
            holder.tvCardHomeTopSoldName.setText(strName);
        }

        StorageReference imageRef = FirebaseStorage.getInstance().getReference("images/products/"+model.getName()+"/"+model.getName()+".jpg");
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).resize(holder.imgCardHomeTopSold.getWidth(), holder.imgCardHomeTopSold.getHeight()).into(holder.imgCardHomeTopSold);
            }
        });


        holder.cardHomeTopSold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productId = "";
                productId = FirebaseDatabase.getInstance().getReference().child("Products")
                        .child(getRef(position).getKey()).getKey();
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("productId",productId);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public Products getItem(int position) {
        return super.getItem(getItemCount() - 1 - position);
    }
    @NonNull
    @Override
    public HomeTopSoldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_home_top_sold, parent, false);
        return new HomeTopSoldViewHolder(view);
    }

    public class HomeTopSoldViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCardHomeTopSoldName,tvCardHomeTopSoldPrice;
        private ImageView imgCardHomeTopSold;
        private CardView cardHomeTopSold;

        public HomeTopSoldViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardHomeTopSoldName=itemView.findViewById(R.id.tvCardHomeTopSoldName);
            tvCardHomeTopSoldPrice=itemView.findViewById(R.id.tvCardHomeTopSoldPrice);
            imgCardHomeTopSold=itemView.findViewById(R.id.imgCardHomeTopSold);
            cardHomeTopSold=itemView.findViewById(R.id.cardHomeTopSold);
        }
    }
}
