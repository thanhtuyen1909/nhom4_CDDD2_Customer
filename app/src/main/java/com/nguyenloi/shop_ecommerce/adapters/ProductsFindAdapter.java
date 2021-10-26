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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nguyenloi.shop_ecommerce.Class.AllFavoriteUser;
import com.nguyenloi.shop_ecommerce.Class.AllProducts;
import com.nguyenloi.shop_ecommerce.Class.Favorite;
import com.nguyenloi.shop_ecommerce.Class.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.Class.Products;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.activites.Other.DetailProductActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductsFindAdapter extends RecyclerView.Adapter<ProductsFindAdapter.ProductsFindViewHolder> {
    Context mContext;
    ArrayList<Products> arrProducts;
    Query queryByFavorite;
    ArrayList<Favorite> arrFavorite;
    private String productId, userId, keyFavorite;
    Favorite favor;

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
    public void onBindViewHolder(@NonNull ProductsFindViewHolder holder, @SuppressLint("RecyclerView") int position) {
        arrFavorite = new ArrayList<>();
        queryByFavorite = FirebaseDatabase.getInstance().getReference()
                .child("Favorite").orderByChild("userId").equalTo(GlobalIdUser.userId);
        //Load all data
        loadDataFavoriteUser();

        Products model = arrProducts.get(position);
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

        holder.imgListProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), model.getKey() + "-----" + model.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        //Update user tym products
        holder.imgListProductsTym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!checkInsertTym(model.getKey())) {
                        insertTymFirebase(model.getKey());
                    } else {
                        Toast.makeText(mContext, "Bạn đã thêm sản phẩm này vào danh sách yêu thích rồi", Toast.LENGTH_SHORT).show();
                    }
                    holder.imgListProductsTym.setImageResource(R.drawable.ic_favorite);
                } catch (Exception ex) {
                    Toast.makeText(v.getContext(), "Thêm vào yêu thích thất bại bạn có thể thử lại", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Go to page detail
        holder.imgListProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productId = "";
                productId = model.getKey();
                Intent intent = new Intent(mContext, DetailProductActivity.class);
                intent.putExtra("productId",productId);
                mContext.startActivity(intent);
            }
        });

        holder.tvListProductsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productId = "";
                productId = model.getKey();
                Intent intent = new Intent(mContext, DetailProductActivity.class);
                intent.putExtra("productId",productId);
                mContext.startActivity(intent);
            }
        });

        //Load status
    }

    private boolean checkInsertTym(String id) {
        for (int i = 0; i < arrFavorite.size(); i++) {
            if (arrFavorite.get(i).getProductId().equals(id)) {
                return true;
            }
        }
        return false;
    }



    private void loadDataFavoriteUser() {
        queryByFavorite.addValueEventListener(new ValueEventListener() {
            boolean processDone = false;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrFavorite.clear();
                if (!processDone && snapshot.exists()) {
                    arrFavorite.clear();
                    for (DataSnapshot favorite : snapshot.getChildren()) {
                        productId = favorite.getValue(Favorite.class).getProductId();
                        userId = favorite.getValue(Favorite.class).getUserId();
                        keyFavorite = favorite.getKey();
                        favor = new Favorite(productId, userId, keyFavorite);
                        arrFavorite.add(favor);
                        processDone = true;
                    }
                } else {
                    processDone = true;
                }
                //Filter data for recycler view
                if (processDone) {
                    //Handle until load complete data
                    processDone = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void insertTymFirebase(@NonNull String productId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", GlobalIdUser.userId);
        map.put("productId", productId);
        FirebaseDatabase.getInstance().getReference().child("Favorite")
                .push().setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }


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
