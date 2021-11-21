package vn.edu.tdc.zuke_customer.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import vn.edu.tdc.zuke_customer.data_models.Offer;
import vn.edu.tdc.zuke_customer.data_models.OfferDetail;
import vn.edu.tdc.zuke_customer.data_models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    ArrayList<Product> items;
    ProductAdapter.ItemClick itemClick;
    int maxSale = 0;
    DatabaseReference offerDetailRef = FirebaseDatabase.getInstance().getReference("Offer_Details");
    DatabaseReference offerRef = FirebaseDatabase.getInstance().getReference("Offers");

    public ProductAdapter(Context context, ArrayList<Product> items) {
        this.context = context;
        this.items = items;
    }

    public void setItemClickListener(ProductAdapter.ItemClick itemClickListener) {
        this.itemClick = itemClickListener;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_goiy, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product item = items.get(position);
        holder.itemTitle.setText(item.getName());
        holder.itemImage.setImageResource(R.drawable.app);
        // Load ảnh
        StorageReference imageRef = FirebaseStorage.getInstance().getReference("images/products/" + item.getName() + "/" + item.getImage());
        imageRef.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri.toString()).fit().into(holder.itemImage));
        //Giá
        offerDetailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<OfferDetail> list = new ArrayList<>();
                holder.txtDiscount.setText("");
                maxSale = 0;
                boolean check = true;
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    OfferDetail offerDetail = snapshot1.getValue(OfferDetail.class);
                    if(offerDetail.getProductID().equals(item.getKey())) {
                        check = false;
                        offerRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
                                    int discount = item.getPrice() / 100 * (100 - maxSale);
                                    holder.itemPrice.setText(formatPrice(discount));
                                    holder.itemPriceMain.setText(formatPrice(item.getPrice()));
                                    holder.itemPriceMain.setPaintFlags(holder.itemPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                } else {
                                    holder.txtDiscount.setVisibility(View.INVISIBLE);
                                    holder.itemPrice.setText(formatPrice(item.getPrice()));
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
                    holder.itemPrice.setText(formatPrice(item.getPrice()));
                    holder.itemPriceMain.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Rating:
        holder.itemRating.setText("");
        if (item.getRating() > 0.0f) {
            holder.itemRating.setText(item.getRating() + "");
        } else {
            holder.itemRating.setVisibility(View.GONE);
        }
        //Đã bán:
        holder.itemRatingAmount.setText("");
        if (item.getSold() > 0) {
            holder.itemRatingAmount.setText(item.getSold() + " đã bán");
        } else {
            holder.itemRatingAmount.setVisibility(View.INVISIBLE);
        }
        if (item.getQuantity() == 0) {
            holder.txtSoldOff.setVisibility(View.VISIBLE);
        } else {
            holder.txtSoldOff.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(v -> {
            if (itemClick != null) {
                itemClick.getDetailProduct(item);
            } else return;
        });
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

    @Override
    public int getItemCount() {
        if(items.size() < 6){
            return items.size();
        }
        else{
            return 6;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView itemTitle, itemPrice, itemPriceMain, itemRating, itemRatingAmount, txtDiscount, txtSoldOff;

        public ViewHolder(View view) {
            super(view);
            itemImage = view.findViewById(R.id.item_image);
            itemTitle = view.findViewById(R.id.item_title);
            itemPrice = view.findViewById(R.id.item_price);
            itemPriceMain = view.findViewById(R.id.item_price_main);
            itemRating = view.findViewById(R.id.item_rating);
            itemRatingAmount = view.findViewById(R.id.item_rating_amount);
            txtDiscount = view.findViewById(R.id.textDiscount);
            txtSoldOff = view.findViewById(R.id.textSoldOff);
        }
    }

    public interface ItemClick {
        void getDetailProduct(Product item);
    }
}
