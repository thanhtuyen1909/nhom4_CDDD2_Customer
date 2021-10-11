package com.nguyenloi.shop_ecommerce.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.TheCustomerConslutant;

public class TheCustomerConslutantAdapter extends ArrayAdapter<TheCustomerConslutant> {
    Activity context;
    TextView tvItemQuestion;
    int resource;
    public TheCustomerConslutantAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= this.context.getLayoutInflater();
        View customView = inflater.inflate(this.resource,null);
        tvItemQuestion=customView.findViewById(R.id.tvItemQuestion);
        TheCustomerConslutant cons = getItem(position);
        tvItemQuestion.setText(cons.getQuestion()+"");
        return customView;
    }
}
