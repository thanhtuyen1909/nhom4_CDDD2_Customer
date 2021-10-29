package vn.edu.tdc.zuke_customer.adapters;

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
import vn.edu.tdc.zuke_customer.Class.Category;
import vn.edu.tdc.zuke_customer.Class.SearchHandle;
import vn.edu.tdc.zuke_customer.R;
import vn.edu.tdc.zuke_customer.activites.Other.FindProductActivity;
import com.squareup.picasso.Picasso;

public class ProductsHomeCategoryAdapter extends FirebaseRecyclerAdapter<Category, ProductsHomeCategoryAdapter.ProductsCategoryViewHolder> {
    Context context;

    public ProductsHomeCategoryAdapter(@NonNull FirebaseRecyclerOptions<Category> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductsCategoryViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Category model) {
        holder.tvCardHomeCategory.setText(model.getName());
        StorageReference imageRef = FirebaseStorage.getInstance().getReference("images/categories/"+model.getImage());
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).resize(holder.imgCardHomeCategory.getWidth(), holder.imgCardHomeCategory.getHeight()).into(holder.imgCardHomeCategory);
            }
        });

        //Search by category
        holder.cardHomeCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idCategory = "";
                idCategory = FirebaseDatabase.getInstance().getReference().child("Categories")
                        .child(getRef(position).getKey()).getKey();
                SearchHandle.setIdCategory(idCategory);
                SearchHandle.setTypeActivity("Category");
                Intent i = new Intent(context, FindProductActivity.class);
                context.startActivity(i);
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
