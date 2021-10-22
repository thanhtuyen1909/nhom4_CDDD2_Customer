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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nguyenloi.shop_ecommerce.Class.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.Class.Products;
import com.nguyenloi.shop_ecommerce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductsFavoriteAdapter extends RecyclerView.Adapter<ProductsFavoriteAdapter.ProductsFavoriteViewHolder> {
    Context mContext;
    ArrayList<Products> arrProducts;
    String idProduct="";

    public ProductsFavoriteAdapter(Context mContext, ArrayList<Products> arrProducts) {
        this.mContext = mContext;
        this.arrProducts = arrProducts;
    }

    @NonNull
    @Override
    public ProductsFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_products, parent, false);
        return new ProductsFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsFavoriteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Products model  = arrProducts.get(position);
        holder.tvListProductsSold.setText("Đã bán: " + model.getSold());
        holder.tvListProductsPrice.setText(model.getPrice() + " đ");


        if (model.getName().length() < 14) {
            holder.tvListProductsName.setText(model.getName());
        } else {
            String strName = model.getName().substring(0, 12) + "..";
            holder.tvListProductsName.setText(strName);
        }
        holder.imgListProductsTym.setImageResource(R.drawable.ic_favorite_border);
        //Image
        StorageReference imageRef = FirebaseStorage.getInstance().getReference("images/products/" + model.getName() + "/" + model.getName() + ".jpg");
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).resize(holder.imgListProducts.getWidth(), holder.imgListProducts.getHeight()).into(holder.imgListProducts);
            }
        });

        //Update user tym products
        holder.imgListProductsTym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyFavorite="";

                FirebaseDatabase.getInstance().getReference().child("Favorite");
//                        .child().removeValue();
            }
        });
    }

//private void FindKeyFavorite(){
//    FirebaseDatabase.getInstance().getReference().child("Favorite")
//            .orderByChild("userId").equalTo(GlobalIdUser.userId).addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            for (DataSnapshot childSnapshot: snapshot.getChildren()) {
//                idProduct = childSnapshot.getKey();
//                for()
//            }
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//    });
//}





    @Override
    public int getItemCount() {
        return arrProducts.size();
    }


    public class ProductsFavoriteViewHolder extends RecyclerView.ViewHolder {
        private TextView tvListProductsName, tvListProductsPrice, tvListProductsSold;
        private ImageView imgListProducts, imgListProductsTym;

        public ProductsFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListProductsPrice = itemView.findViewById(R.id.tvListProductsPrice);
            tvListProductsName = itemView.findViewById(R.id.tvListProductsName);
            tvListProductsSold = itemView.findViewById(R.id.tvListProductsSold);
            imgListProducts = itemView.findViewById(R.id.imgListProducts);
            imgListProductsTym = itemView.findViewById(R.id.imgListProductsTym);
        }
    }
}
