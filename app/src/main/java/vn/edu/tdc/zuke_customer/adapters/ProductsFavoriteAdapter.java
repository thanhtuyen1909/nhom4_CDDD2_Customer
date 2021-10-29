package vn.edu.tdc.zuke_customer.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import vn.edu.tdc.zuke_customer.Class.AllFavoriteUser;
import vn.edu.tdc.zuke_customer.Class.Products;
import vn.edu.tdc.zuke_customer.R;
import vn.edu.tdc.zuke_customer.activites.Other.DetailProductActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductsFavoriteAdapter extends RecyclerView.Adapter<ProductsFavoriteAdapter.ProductsFavoriteViewHolder> {
    Context mContext;
    ArrayList<Products> arrProducts;

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
        Products model = arrProducts.get(position);
        holder.tvListProductsSold.setText("Đã bán: " + model.getSold());
        holder.tvListProductsPrice.setText(model.getPrice() + " đ");

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

        if (model.getName().length() < 14) {
            holder.tvListProductsName.setText(model.getName());
        } else {
            String strName = model.getName().substring(0, 12) + "..";
            holder.tvListProductsName.setText(strName);
        }
        holder.imgListProductsTym.setImageResource(R.drawable.ic_favorite);
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
                AlertDialog.Builder btn = new AlertDialog.Builder(v.getContext());
                btn.setMessage("Bạn có muốn xoá sản phẩm này ra khỏi danh sách yêu thích không? ");
                btn.setIcon(R.drawable.ic_launcher_background);
                btn.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String keyFavorite = FindKeyFavoriteToDelete(model.getKey());
                        try{
                            FirebaseDatabase.getInstance().getReference().child("Favorite")
                                    .child(keyFavorite).removeValue();
                        }catch (Exception e){

                        }
                    }
                });
                btn.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                btn.create().show();


            }
        });
    }

    private String FindKeyFavoriteToDelete(String productId) {
        for (int i = 0; i < AllFavoriteUser.getArrAllFavoriteUser().size(); i++) {
            if (productId.equals(AllFavoriteUser.getArrAllFavoriteUser().get(i).getProductId())) {
                return AllFavoriteUser.getArrAllFavoriteUser().get(i).getKey();
            }
        }
        return null;
    }


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
