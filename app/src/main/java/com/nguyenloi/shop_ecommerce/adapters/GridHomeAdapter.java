package com.nguyenloi.shop_ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nguyenloi.shop_ecommerce.R;
import java.util.ArrayList;

public class GridHomeAdapter extends BaseAdapter {
    ArrayList<String> nameProduct, priceProduct;
    ArrayList<Integer> imgProduct;
    Context context;
    View view;


    public GridHomeAdapter(Context context, ArrayList<String> nameProduct, ArrayList<String> priceProduct, ArrayList<Integer> imgProduct) {
        this.context=context;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.imgProduct = imgProduct;
    }

    @Override
    public int getCount() {
        return nameProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            view = new View(context);
            view = inflater.inflate(R.layout.gridview_fragment_home, null);
            TextView tvGridHomeName = view.findViewById(R.id.tvGridHomeName);
            TextView tvGridHomePrice =  view.findViewById(R.id.tvGridHomePrice);
            ImageView imgGridHome =  view.findViewById(R.id.imgGridHome);
            tvGridHomeName.setText(nameProduct.get(position));
            imgGridHome.setImageResource(imgProduct.get(position));
            tvGridHomePrice.setText(priceProduct.get(position)+"đ");
        }
        return view;
    }
}
