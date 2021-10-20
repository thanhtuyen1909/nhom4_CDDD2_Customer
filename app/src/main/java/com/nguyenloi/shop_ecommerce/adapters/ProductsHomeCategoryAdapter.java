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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.nguyenloi.shop_ecommerce.Category;
import com.nguyenloi.shop_ecommerce.R;
import com.squareup.picasso.Picasso;

public class ProductsHomeCategoryAdapter extends FirebaseRecyclerAdapter<Category, ProductsHomeCategoryAdapter.ProductsCategoryViewHolder> {
    Context context;

    public ProductsHomeCategoryAdapter(@NonNull FirebaseRecyclerOptions<Category> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductsCategoryViewHolder holder, int position, @NonNull Category model) {
        holder.tvCardHomeCategory.setText(model.getName());
        Picasso.get().load(model.getImage()).into(holder.imgCardHomeCategory);

        //Search by category
        holder.cardHomeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @NonNull
    @Override
    public ProductsCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_home_category, parent, false);
        return new ProductsCategoryViewHolder(view);
    }

    public class ProductsCategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCardHomeCategory;
        private ImageView imgCardHomeCategory;
        private CardView cardHomeCategory;

        public ProductsCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCardHomeCategory = itemView.findViewById(R.id.imgCardHomeCategory);
            tvCardHomeCategory = itemView.findViewById(R.id.tvCardHomeCategory);
            cardHomeCategory = itemView.findViewById(R.id.cardHomeCategory);
        }
    }
}
