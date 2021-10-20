package com.nguyenloi.shop_ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nguyenloi.shop_ecommerce.Category;
import com.nguyenloi.shop_ecommerce.Favorite;
import com.nguyenloi.shop_ecommerce.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.Products;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.UserLogin;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProductsFindAdapter extends FirebaseRecyclerAdapter<Products, ProductsFindAdapter.ProductsFindViewHolder> {
    Context context;
    Query queryFilterTym;
    String productId = "";


    public ProductsFindAdapter(@NonNull FirebaseRecyclerOptions<Products> options, Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public ProductsFindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_products, parent, false);
        return new ProductsFindViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductsFindViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Products model) {
        productId =FirebaseDatabase.getInstance().getReference().child("Products")
                .child(getRef(position).getKey()).getKey();

        holder.tvListProductsSold.setText("Đã bán: "+model.getSold());
        holder.tvListProductsPrice.setText(model.getPrice()+" đ");
        if(model.getName().length()<14){
            holder.tvListProductsName.setText(model.getName());
        }else{
            String strName = model.getName().substring(0,12)+"..";
            holder.tvListProductsName.setText(strName);
        }


        //Image
        StorageReference imageRef = FirebaseStorage.getInstance().getReference("images/products/"+model.getName()+"/"+model.getName()+".jpg");
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
                queryFilterTym = FirebaseDatabase.getInstance().getReference()
                        .child("Favorite").orderByChild("userId").equalTo(GlobalIdUser.userId);
                queryFilterTym.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Favorite favorite = snapshot.getValue(Favorite.class);
                        if (snapshot.exists()) {
                            // dataSnapshot is the "issue" node with all children with id 0
                            for (DataSnapshot issue : snapshot.getChildren()) {
                                if(issue.getValue(Favorite.class).getProductId().equals(productId)){
                                   updateTym(holder);
                                }else{

                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }



    private void updateTym(@NonNull ProductsFindViewHolder holder){
        Map<String, Object> map = new HashMap<>();
        map.put("userId", GlobalIdUser.userId);
        map.put("productId", productId);
        FirebaseDatabase.getInstance().getReference().child("Favorite")
                .push().setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        holder.imgListProductsTym.setImageResource(R.drawable.ic_favorite);
                    }
                });
    }


    public class ProductsFindViewHolder extends RecyclerView.ViewHolder {
        private TextView tvListProductsName,tvListProductsPrice,tvListProductsSold;
        private ImageView imgListProducts,imgListProductsTym;

        public ProductsFindViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListProductsPrice=itemView.findViewById(R.id.tvListProductsPrice);
            tvListProductsName=itemView.findViewById(R.id.tvListProductsName);
            tvListProductsSold=itemView.findViewById(R.id.tvListProductsSold);
            imgListProducts=itemView.findViewById(R.id.imgListProducts);
            imgListProductsTym=itemView.findViewById(R.id.imgListProductsTym);
        }
    }
}
