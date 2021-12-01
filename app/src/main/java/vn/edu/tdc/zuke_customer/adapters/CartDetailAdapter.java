package vn.edu.tdc.zuke_customer.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vn.edu.tdc.zuke_customer.R;
import vn.edu.tdc.zuke_customer.data_models.CartDetail;
import vn.edu.tdc.zuke_customer.data_models.Offer;
import vn.edu.tdc.zuke_customer.data_models.OfferDetail;
import vn.edu.tdc.zuke_customer.data_models.Product;

public class CartDetailAdapter extends RecyclerView.Adapter<CartDetailAdapter.ViewHolder> {
    Context context;
    ArrayList<CartDetail> list;
    ItemClickListener itemClickListener;
    DatabaseReference proRef = FirebaseDatabase.getInstance().getReference("Products");
    DatabaseReference offerDetailRef = FirebaseDatabase.getInstance().getReference("Offer_Details");
    DatabaseReference offerRef = FirebaseDatabase.getInstance().getReference("Offers");
    DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Cart");
    DatabaseReference cartDetailRef = FirebaseDatabase.getInstance().getReference("Cart_Detail");
    ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    int maxSale = 0;

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
        View itemView = inflater.inflate(R.layout.item_product_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartDetail item = list.get(position);
        holder.itemName.setText("");
        holder.itemPrice.setText("");
        holder.itemPriceDiscount.setText("");
        holder.edtValue.setText("");
        proRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot node : snapshot.getChildren()) {
                    Product product = node.getValue(Product.class);
                    if (node.getKey().equals(item.getProductID())) {
                        if (product.getStatus() == -1) {
                            cartDetailRef.child(item.getKey()).removeValue();
                            cartRef.child(item.getCartID()).child("total").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    cartRef.child(item.getCartID()).child("total").setValue(snapshot.getValue(Integer.class) - item.getPrice() * item.getAmount());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else {
                            //set name
                            holder.itemName.setText(product.getName());
                            //set gia san pham
                            holder.itemPrice.setText(formatPrice(product.getPrice()));
                            offerDetailRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    ArrayList<OfferDetail> list = new ArrayList<>();
                                    holder.txtDiscount.setText("");
                                    boolean check = true;
                                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                        OfferDetail offerDetail = snapshot1.getValue(OfferDetail.class);
                                        if (offerDetail.getProductID().equals(node.getKey())) {
                                            check = false;
                                            offerRef.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    maxSale = 0;
                                                    for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                                                        Offer offer = snapshot2.getValue(Offer.class);
                                                        offer.setKey(snapshot2.getKey());
                                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                                        long startDay = sdf.parse(offer.getStartDate(), new ParsePosition(0)).getTime();
                                                        long endtDay = sdf.parse(offer.getEndDate(), new ParsePosition(0)).getTime();
                                                        long now = new Date().getTime();
                                                        if (startDay == endtDay) {
                                                            endtDay += 1000 * 60 * 60 * 24 - 1;
                                                        }
                                                        if (offer.getKey().equals(offerDetail.getOfferID()) && now <= endtDay && now >= startDay) {
                                                            list.add(offerDetail);
                                                        }
                                                    }
                                                    for (OfferDetail offerDetail : list) {
                                                        if (offerDetail.getPercentSale() > maxSale) {
                                                            maxSale = offerDetail.getPercentSale();
                                                        }
                                                    }
                                                    if (maxSale != 0) {
                                                        holder.txtDiscount.setVisibility(View.VISIBLE);
                                                        holder.txtDiscount.setText(maxSale + "%");

                                                        holder.itemPrice.setVisibility(View.VISIBLE);

                                                        int discount = product.getPrice() / 100 * (100 - maxSale);
                                                        holder.itemPriceDiscount.setText(formatPrice(discount));
                                                        holder.itemPrice.setText(formatPrice(product.getPrice()));
                                                        holder.itemPrice.setPaintFlags(holder.itemPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                                    } else {
                                                        holder.txtDiscount.setVisibility(View.INVISIBLE);
                                                        holder.itemPriceDiscount.setText(formatPrice(product.getPrice()));
                                                        holder.itemPrice.setVisibility(View.INVISIBLE);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }
                                    if (check) {
                                        holder.txtDiscount.setVisibility(View.INVISIBLE);
                                        holder.itemPriceDiscount.setText(formatPrice(product.getPrice()));
                                        holder.itemPrice.setVisibility(View.INVISIBLE);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            holder.edtValue.setText("" + item.getAmount());
                            //set hinh anh
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            final long ONE_MEGABYTE = 1024 * 1024;
                            StorageReference imageRef = storage.getReference("images/products/" + product.getName() + "/" + product.getImage());
                            imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
                                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                holder.itemImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, holder.itemImage.getWidth(), holder.itemImage.getHeight(), false));
                            });

                            //check hết hàng
                            if (product.getQuantity() == 0) {
                                holder.txtSoldOff.setVisibility(View.VISIBLE);
                            } else {
                                holder.txtSoldOff.setVisibility(View.INVISIBLE);
                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.onClickListener = v -> {
            if (itemClickListener != null) {
                int value = Integer.parseInt(String.valueOf(holder.edtValue.getText()));
                if (v == holder.btnAdd) {
                    value++;
                    holder.edtValue.setText(String.valueOf(value));
                    itemClickListener.changeQuantity(item, value);
                }
                if (v == holder.btnMinus) {
                    value--;
                    holder.edtValue.setText(String.valueOf(value));
                    itemClickListener.changeQuantity(item, value);
                }
                if (v == holder.cardView)
                    itemClickListener.delete(item.getKey(), formatInt(holder.itemPriceDiscount.getText().toString()));

            } else {
                return;
            }
        };
        viewBinderHelper.bind(holder.swipeRevealLayout, item.getKey());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SwipeRevealLayout swipeRevealLayout;
        CardView cardView;
        ImageView itemImage;
        TextView itemName, itemPrice, itemPriceDiscount, txtDiscount, txtSoldOff;
        Button btnAdd, btnMinus;
        EditText edtValue;
        View.OnClickListener onClickListener;

        public ViewHolder(View view) {
            super(view);
            swipeRevealLayout = view.findViewById(R.id.swipelayout);
            cardView = view.findViewById(R.id.cardView);
            itemImage = view.findViewById(R.id.img);
            itemName = view.findViewById(R.id.txt_name);
            itemPrice = view.findViewById(R.id.txt_price);
            itemPriceDiscount = view.findViewById(R.id.txtPriceDiscount);
            itemPriceDiscount.setText("");
            btnAdd = view.findViewById(R.id.plusButton);
            btnMinus = view.findViewById(R.id.minusButton);
            edtValue = view.findViewById(R.id.valueAmount);

            txtDiscount = view.findViewById(R.id.textDiscount);
            txtSoldOff = view.findViewById(R.id.textSoldOff);

            btnMinus.setOnClickListener(this);
            btnAdd.setOnClickListener(this);
            cardView.setOnClickListener(this);
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

        void delete(String id, int price);
    }

    private String formatPrice(int price) {
        String stmp = String.valueOf(price);
        int amount;
        amount = (int) (stmp.length() / 3);
        if (stmp.length() % 3 == 0)
            amount--;
        for (int i = 1; i <= amount; i++) {
            stmp = new StringBuilder(stmp).insert(stmp.length() - (i * 3) - (i - 1), ",").toString();
        }
        return stmp + " ₫";
    }

    private int formatInt(String price) {
        return Integer.parseInt(price.substring(0, price.length() - 2).replace(",", ""));
    }
}
