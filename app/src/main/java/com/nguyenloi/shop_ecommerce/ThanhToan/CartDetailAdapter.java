package com.nguyenloi.shop_ecommerce.ThanhToan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyenloi.shop_ecommerce.Cart.Product;
import com.nguyenloi.shop_ecommerce.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartDetailAdapter extends RecyclerView.Adapter<CartDetailAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CartDetail> list;
    ItemClickListener itemClickListener;
    DatabaseReference proRef = FirebaseDatabase.getInstance().getReference("Products");
    DatabaseReference promoRef = FirebaseDatabase.getInstance().getReference("Offer_Details");

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CartDetailAdapter(Context context, ArrayList<CartDetail> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CartDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_history_bill_detail, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartDetail item = list.get(position);
        proRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot node : snapshot.getChildren()) {
                    Product product = node.getValue(Product.class);
                    if (node.getKey().equals(item.getProductID())) {
                        //set name
                        holder.tvName.setText(product.getName());
                        //set gia san pham
                        holder.tvTotal.setText(formatPrice(item.getTotalPrice()));
                        holder.tvPrice.setText(formatPrice(item.getTotalPrice()/item.getAmount()));
                        //set hinh anh
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        final long ONE_MEGABYTE = 1024 * 1024;
                        StorageReference imageRef = storage.getReference("images/products/" + product.getName() + "/" + product.getImage());
                        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                holder.image.setImageBitmap(Bitmap.createScaledBitmap(bmp, holder.image.getWidth(), holder.image.getHeight(), false));
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName,tvPrice,tvAmount,tvTotal;
        ImageView image;
        View.OnClickListener onClickListener;
        public ViewHolder(View view) {
            super(view);
            tvName=view.findViewById(R.id.tvItemBillDetailName);
            tvPrice=view.findViewById(R.id.tvItemBillDetailPrice);
            tvAmount=view.findViewById(R.id.tvItemBillDetailAmount);
            tvTotal = view.findViewById(R.id.tvItemBillDetailTotalPrice);
            image=view.findViewById(R.id.imgItemBillDetail);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
        }
    }

    public interface ItemClickListener {
        void changeQuantity(CartDetail item, int value);

        void delete(String id);
    }

    private String formatPrice(int price) {
        return NumberFormat.getCurrencyInstance(new Locale("vi", "VN"))
                .format(price);
    }
}