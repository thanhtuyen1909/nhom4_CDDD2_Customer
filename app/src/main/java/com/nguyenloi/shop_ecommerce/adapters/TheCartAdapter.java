package com.nguyenloi.shop_ecommerce.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.TheCart;

public class TheCartAdapter extends ArrayAdapter<TheCart> {
    Activity context;
    int resource;
    EditText edtItemCartAmount;
    ImageButton btnItemCartAdd, btnItemCartRemove;
    TextView tvItemCartName, tvItemCartPrice;
    ImageView imgItemCart;
    public TheCartAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View customView= inflater.inflate(this.resource,null);
        imgItemCart=customView.findViewById(R.id.imgItemCart);
        tvItemCartName=customView.findViewById(R.id.tvItemCartName);
        tvItemCartPrice=customView.findViewById(R.id.tvItemCartPrice);
        edtItemCartAmount=customView.findViewById(R.id.edtItemCartAmount);
        TheCart cart = getItem(position);
        tvItemCartPrice.setText(cart.getPrice()+"");
        tvItemCartName.setText(cart.getName()+"");
        edtItemCartAmount.setText(cart.getAmout()+"");
        imgItemCart.setImageResource(cart.getImage());
        return customView;
    }
}
