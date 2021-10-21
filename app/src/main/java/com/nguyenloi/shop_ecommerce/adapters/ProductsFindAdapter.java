package com.nguyenloi.shop_ecommerce.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nguyenloi.shop_ecommerce.Class.Products;
import com.nguyenloi.shop_ecommerce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductsFindAdapter extends RecyclerView.Adapter<ProductsFindAdapter.ProductsFindViewHolder> {
    Context mContext;
    ArrayList<Products> arrProducts;

    public ProductsFindAdapter(Context mContext, ArrayList<Products> arrProducts) {
        this.mContext = mContext;
        this.arrProducts = arrProducts;
    }

    @NonNull
    @Override
    public ProductsFindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_products, parent, false);
        return new ProductsFindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsFindViewHolder holder, int position) {
        Products model  = arrProducts.get(position);
        holder.tvListProductsSold.setText("Đã bán: " + model.getSold());
        holder.tvListProductsPrice.setText(model.getPrice() + " đ");
        if (model.getName().length() < 14) {
            holder.tvListProductsName.setText(model.getName());
        } else {
            String strName = model.getName().substring(0, 12) + "..";
            holder.tvListProductsName.setText(strName);
        }

        //Image
        StorageReference imageRef = FirebaseStorage.getInstance().getReference("images/products/" + model.getName() + "/" + model.getName() + ".jpg");
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).resize(holder.imgListProducts.getWidth(), holder.imgListProducts.getHeight()).into(holder.imgListProducts);
            }
        });

        //Delete icon position

        //Update user tym products
        holder.imgListProductsTym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imgListProductsTym.setImageResource(R.drawable.ic_favorite);
            }
        });
    }

//    private void updateTym(@NonNull ProductsFindViewHolder holder) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("userId", GlobalIdUser.userId);
//        map.put("productId", productId);
//        FirebaseDatabase.getInstance().getReference().child("Favorite")
//                .push().setValue(map)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        holder.imgListProductsTym.setImageResource(R.drawable.ic_favorite);
//                    }
//                });
//    }



    @Override
    public int getItemCount() {
        return arrProducts.size();
    }


    public class ProductsFindViewHolder extends RecyclerView.ViewHolder {
        private TextView tvListProductsName, tvListProductsPrice, tvListProductsSold;
        private ImageView imgListProducts, imgListProductsTym;

        public ProductsFindViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListProductsPrice = itemView.findViewById(R.id.tvListProductsPrice);
            tvListProductsName = itemView.findViewById(R.id.tvListProductsName);
            tvListProductsSold = itemView.findViewById(R.id.tvListProductsSold);
            imgListProducts = itemView.findViewById(R.id.imgListProducts);
            imgListProductsTym = itemView.findViewById(R.id.imgListProductsTym);
        }
    }
}
