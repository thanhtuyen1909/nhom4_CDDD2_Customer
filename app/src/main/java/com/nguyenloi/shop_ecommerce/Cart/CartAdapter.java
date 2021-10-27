package com.nguyenloi.shop_ecommerce.Cart;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyenloi.shop_ecommerce.R;

import com.nguyenloi.shop_ecommerce.ThanhToan.CartDetail;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CartDetail> list;
    CartAdapter.ItemClickListener itemClickListener;
    DatabaseReference proRef = FirebaseDatabase.getInstance().getReference("Products");
    DatabaseReference promoRef = FirebaseDatabase.getInstance().getReference("Offer_Details");

    public void setItemClickListener(CartAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CartAdapter(Context context, ArrayList<CartDetail> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_cart, parent, false);
        CartAdapter.ViewHolder viewHolder = new CartAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        CartDetail item = list.get(position);
        proRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot node : snapshot.getChildren()) {
                    Product product = node.getValue(Product.class);
                    Log.d("TAG", "onDataChange: "+product.getName());
                    if (node.getKey().equals(item.getProductID())) {
                        //set name

                        holder.tvName.setText(product.getName());
                        //set gia san pham
                        holder.tvPrice.setText(formatPrice(item.getTotalPrice()));
                        holder.edtAmount.setText(item.getAmount()+"");
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
        TextView tvName,tvPrice;
        EditText edtAmount;
        ImageView image;
        ImageButton btnAdd,btnDelete;
        View.OnClickListener onClickListener;
        public ViewHolder(View view) {
            super(view);
            tvName=view.findViewById(R.id.tvItemCartName);
            tvPrice=view.findViewById(R.id.tvItemCartPrice);
            edtAmount=view.findViewById(R.id.edtItemCartAmount);
            image=view.findViewById(R.id.imgCart);
            btnAdd=view.findViewById(R.id.btnItemCartAdd);
            btnDelete=view.findViewById(R.id.btnItemCartRemove);
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