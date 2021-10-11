package com.nguyenloi.shop_ecommerce.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nguyenloi.shop_ecommerce.TheBill;
import com.nguyenloi.shop_ecommerce.R;

public class TheBillAdapter extends ArrayAdapter<TheBill> {
    Activity context;
    int resource;
    TextView tvBillStatus, tvBillBookingDate, tvBillReceivedDate;

    public TheBillAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View customView = inflater.inflate(this.resource, null);
        tvBillStatus = customView.findViewById(R.id.tvBillStatus);
        tvBillBookingDate = customView.findViewById(R.id.tvBillBookingDate);
        tvBillReceivedDate = customView.findViewById(R.id.tvBillReceivedBill);
        TheBill bill = getItem(position);
        tvBillBookingDate.setText("Ngày đặt: "+bill.getBookingDate());
        tvBillStatus.setText("Tình trạng: "+bill.getStatus());
        tvBillReceivedDate.setText("Ngày nhận: "+bill.getReceivedDate());
        return customView;
    }
}
