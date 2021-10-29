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
import vn.edu.tdc.zuke_customer.Class.Products;
import vn.edu.tdc.zuke_customer.R;
import vn.edu.tdc.zuke_customer.activites.Other.DetailProductActivity;
import com.squareup.picasso.Picasso;

public class ProductsHomeSuggestionAdapter extends FirebaseRecyclerAdapter<Products, ProductsHomeSuggestionAdapter.SuggestionViewHolder> {
    private Context context;

    public ProductsHomeSuggestionAdapter(@NonNull FirebaseRecyclerOptions<Products> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull SuggestionViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Products model) {
        holder.tvCardHomeSuggestionPrice.setText(model.getPrice() + "đ");
        if(model.getName().length()<12){
            holder.tvCardHomeSuggestionName.setText(model.getName());
        }else{
            String strName = model.getName().substring(0,11)+"..";
            holder.tvCardHomeSuggestionName.setText(strName);
        }

        StorageReference imageRef = FirebaseStorage.getInstance().getReference("images/products/"+model.getName()+"/"+model.getName()+".jpg");
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri.toString()).resize(holder.imgCardHomeSuggestion.getWidth(), holder.imgCardHomeSuggestion.getHeight()).into(holder.imgCardHomeSuggestion);
            }
        });

        holder.cardHomeSuggestion.setOnClickListener(new View.OnClickListener() {
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

    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_home_suggestions, parent, false);
        return new SuggestionViewHolder(view);

    }

    public class SuggestionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCardHomeSuggestionName,tvCardHomeSuggestionPrice;
        private ImageView imgCardHomeSuggestion;
        private CardView cardHomeSuggestion;

        public SuggestionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCardHomeSuggestionName=itemView.findViewById(R.id.tvCardHomeSuggestionName);
            tvCardHomeSuggestionPrice=itemView.findViewById(R.id.tvCardHomeSuggestionPrice);
            imgCardHomeSuggestion=itemView.findViewById(R.id.imgCardHomeSuggestion);
            cardHomeSuggestion=itemView.findViewById(R.id.cardHomeSuggestion);
        }
    }
}
