package com.nguyenloi.shop_ecommerce.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.TheList;
import com.nguyenloi.shop_ecommerce.TheRating;

public class TheRatingAdapter extends ArrayAdapter<TheRating> {
    Activity context;
    int resource;
    ImageView imgBillDetailRating;
    TextView tvBillDetailRatingNameProduct,tvBillDetailRatingColorProduct;
    EditText edtBillDetailRatingDescription;
    public TheRatingAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View customView= inflater.inflate(this.resource,null);
        imgBillDetailRating=customView.findViewById(R.id.imgBillDetailRating);
        tvBillDetailRatingColorProduct=customView.findViewById(R.id.tvBillDetailRatingColorProduct);
        tvBillDetailRatingNameProduct=customView.findViewById(R.id.tvBillDetailRatingNameProduct);
        edtBillDetailRatingDescription=customView.findViewById(R.id.edtBillDetailRatingDescription);
        TheRating theRating = getItem(position);
        tvBillDetailRatingNameProduct.setText(theRating.getNameProductBillRating()+"");
        tvBillDetailRatingColorProduct.setText("Màu sắc: "+theRating.getColorProductBillRating()+" đ");
        edtBillDetailRatingDescription.setText(theRating.getDescriptionBillRating());
        imgBillDetailRating.setImageResource(theRating.getImageBillRating());
        return  customView;
    }
}
