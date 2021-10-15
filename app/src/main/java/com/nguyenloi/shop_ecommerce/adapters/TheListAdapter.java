package com.nguyenloi.shop_ecommerce.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.TheList;

public class TheListAdapter extends ArrayAdapter<TheList> {
    Activity context;
    int resource;
    ImageView imgList;
    TextView tvListPrice, tvListName, tvListSold;
    public TheListAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View customView= inflater.inflate(this.resource,null);
        imgList=customView.findViewById(R.id.imgList);
        tvListName=customView.findViewById(R.id.tvListName);
        tvListPrice=customView.findViewById(R.id.tvListPrice);
        tvListSold=customView.findViewById(R.id.tvListSold);
        TheList theList = getItem(position);
        tvListSold.setText("Đã bán "+theList.getSoldProduct()+"");
        tvListPrice.setText(theList.getPriceProduct()+" đ");
        tvListName.setText(theList.getNameProduct());
        imgList.setImageResource(theList.getImageProduct());
        return  customView;
    }
}
