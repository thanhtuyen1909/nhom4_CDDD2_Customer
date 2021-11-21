package vn.edu.tdc.zuke_customer.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vn.edu.tdc.zuke_customer.R;
import vn.edu.tdc.zuke_customer.data_models.Favorite;
import vn.edu.tdc.zuke_customer.data_models.Offer;
import vn.edu.tdc.zuke_customer.data_models.OfferDetail;
import vn.edu.tdc.zuke_customer.data_models.Product;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    Context context;
    ArrayList<Favorite> items = new ArrayList<>();
    FavoriteAdapter.ItemClick itemClick;
    Handler handler = new Handler();
    Product product1 = null;
    DatabaseReference productRef = FirebaseDatabase.getInstance().getReference("Products");
    DatabaseReference favRef = FirebaseDatabase.getInstance().getReference("Favorite");
    DatabaseReference offerDetailRef = FirebaseDatabase.getInstance().getReference("Offer_Details");
    DatabaseReference offerRef = FirebaseDatabase.getInstance().getReference("Offers");
    int maxSale = 0;

    public FavoriteAdapter(Context context, ArrayList<Favorite> items) {
        this.context = context;
        this.items = items;
    }

    public void setItemClickListener(FavoriteAdapter.ItemClick itemClickListener) {
        this.itemClick = itemClickListener;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_search_favorite, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        Favorite favorite = items.get(position);
        holder.itemTitle.setText("");
        holder.itemPrice.setText("");
        holder.itemPriceMain.setText("");
        holder.itemRating.setText("");
        holder.itemRatingAmount.setText("");

        // Thông tin sản phẩm:
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot node : snapshot.getChildren()) {
                    Product product = node.getValue(Product.class);
                    product.setKey(node.getKey());
                    if (product.getKey().equals(favorite.getProductId())) {
                        if (product.getStatus() == -1) {
                            favRef.child(favorite.getKey()).removeValue();
                            notifyDataSetChanged();
                        } else {
                            product1 = product;
                            //Tên
                            holder.itemTitle.setText(product.getName());
                            //Giá
                            offerDetailRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    ArrayList<OfferDetail> list = new ArrayList<>();
                                    holder.txtDiscount.setText("");
                                    boolean check = true;
                                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                        OfferDetail offerDetail = snapshot1.getValue(OfferDetail.class);
                                        if(offerDetail.getProductID().equals(product.getKey())) {
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
                                                        holder.itemPriceMain.setVisibility(View.VISIBLE);
                                                        int discount = product.getPrice() / 100 * (100 - maxSale);
                                                        holder.itemPrice.setText(formatPrice(discount));
                                                        holder.itemPriceMain.setText(formatPrice(product.getPrice()));
                                                        holder.itemPriceMain.setPaintFlags(holder.itemPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                                    } else {
                                                        holder.txtDiscount.setVisibility(View.INVISIBLE);
                                                        holder.itemPrice.setText(formatPrice(product.getPrice()));
                                                        holder.itemPriceMain.setVisibility(View.INVISIBLE);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }
                                    if(check) {
                                        holder.txtDiscount.setVisibility(View.INVISIBLE);
                                        holder.itemPrice.setText(formatPrice(product.getPrice()));
                                        holder.itemPriceMain.setVisibility(View.INVISIBLE);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            //Ảnh
                            StorageReference imageRef = FirebaseStorage.getInstance().getReference("images/products/" + product.getName() + "/" + product.getImage());
                            imageRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).resize(holder.itemImage.getWidth(), holder.itemImage.getHeight()).into(holder.itemImage));
                            //Rating:
                            if (product.getRating() > 0) {
                                holder.itemRating.setText(product.getRating() + "");
                            } else {
                                holder.itemRating.setVisibility(View.GONE);
                            }

                            //Đã bán:
                            if (product.getSold() > 0) {
                                holder.itemRatingAmount.setText(product.getSold() + " đã bán");
                            } else {
                                holder.itemRatingAmount.setVisibility(View.INVISIBLE);
                            }

                            //Hết hàng:
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
        holder.bt_favorite.setChecked(true);
        holder.onClickListener = v -> {
            if (itemClick != null) {
                handler.postDelayed(() -> {
                    if (v == holder.bt_favorite) {
                        itemClick.deleteFavorite(favorite.getKey());
                    } else if (v == holder.bt_cart) {
                        if(product1.getQuantity() > 0) {
                            itemClick.addCart(favorite.getProductId(), formatInt(holder.itemPrice.getText() + ""));
                        }
                        else itemClick.addCart("Hết hàng", formatInt(holder.itemPrice.getText() + ""));
                    } else {
                        if (product1 != null) itemClick.detailProduct(product1);
                    }
                }, 50);
            } else {
                return;
            }
        };
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

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ToggleButton bt_favorite;
        ImageView itemImage, bt_cart;
        TextView itemTitle, itemPrice, itemPriceMain, itemRating, itemRatingAmount, txtDiscount, txtSoldOff;
        View.OnClickListener onClickListener;

        public ViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.img);
            itemTitle = view.findViewById(R.id.txt_name);
            itemPrice = view.findViewById(R.id.txt_price);
            itemPriceMain = view.findViewById(R.id.txt_pricemain);
            itemRating = view.findViewById(R.id.item_rating);
            itemRatingAmount = view.findViewById(R.id.item_rating_amount);
            bt_favorite = view.findViewById(R.id.button_favorite);
            bt_cart = view.findViewById(R.id.addCart);

            txtDiscount = view.findViewById(R.id.textDiscount);
            txtSoldOff = view.findViewById(R.id.textSoldOff);

            bt_cart.setOnClickListener(this);
            bt_favorite.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }
        }
    }

    public interface ItemClick {
        void addCart(String productID, int price);

        void deleteFavorite(String key);

        void detailProduct(Product item);
    }
}
