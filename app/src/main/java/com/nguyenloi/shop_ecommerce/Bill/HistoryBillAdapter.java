package com.nguyenloi.shop_ecommerce.Bill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyenloi.shop_ecommerce.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class HistoryBillAdapter extends FirebaseRecyclerAdapter<TheBill,HistoryBillAdapter.BillViewHolder> {
    Context context;

    public HistoryBillAdapter(@NonNull FirebaseRecyclerOptions<TheBill> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull BillViewHolder holder, int position, @NonNull TheBill model) {
        holder.tvStatus.setText(model.getStatus());
        holder.tvBookingDate.setText(model.getBookingDate());
        holder.tvReceivedDate.setText(model.getReceivedDate());
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill,parent,false);

        return new BillViewHolder(view);
    }
    public class BillViewHolder extends RecyclerView.ViewHolder{
        private TextView tvStatus, tvBookingDate, tvReceivedDate;
        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus =itemView.findViewById(R.id.tvBillStatus);
            tvBookingDate =itemView.findViewById(R.id.tvBillBookingDate);
            tvReceivedDate =itemView.findViewById(R.id.tvBillReceivedBill);
        }
    }
}
