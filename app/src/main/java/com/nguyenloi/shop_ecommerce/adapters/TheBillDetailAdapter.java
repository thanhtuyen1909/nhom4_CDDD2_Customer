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
import com.nguyenloi.shop_ecommerce.TheBillHistoryDetail;
import com.nguyenloi.shop_ecommerce.TheCustomerConslutant;

public class TheBillDetailAdapter extends ArrayAdapter<TheBillHistoryDetail> {
    Activity context;
    int resource;
    ImageView imgItemBillDetail;
    TextView tvItemBillDetailName, tvItemBillDetailPrice, tvItemBillDetailAmount, tvItemBillDetailTotalPrice;
    public TheBillDetailAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= this.context.getLayoutInflater();
        View customView = inflater.inflate(this.resource,null);
        imgItemBillDetail=customView.findViewById(R.id.imgItemBillDetail);
        tvItemBillDetailAmount=customView.findViewById(R.id.tvItemBillDetailAmount);
        tvItemBillDetailName=customView.findViewById(R.id.tvItemBillDetailName);
        tvItemBillDetailPrice=customView.findViewById(R.id.tvItemBillDetailPrice);
        tvItemBillDetailTotalPrice=customView.findViewById(R.id.tvItemBillDetailTotalPrice);
        TheBillHistoryDetail detail = getItem(position);
        imgItemBillDetail.setImageResource(detail.getImgDetailBill());
        tvItemBillDetailTotalPrice.setText(detail.getTotalPriceBill()+"");
        tvItemBillDetailName.setText(detail.getNameProduct());
        tvItemBillDetailAmount.setText("Số lượng: "+detail.getAmountBill()+"");
        tvItemBillDetailPrice.setText("Giá: "+detail.getPriceBill()+"");
        return customView;
    }
}
