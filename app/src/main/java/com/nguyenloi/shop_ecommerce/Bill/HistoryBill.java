package com.nguyenloi.shop_ecommerce.Bill;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nguyenloi.shop_ecommerce.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class HistoryBill extends AppCompatActivity {
    RecyclerView rcvTheBill;
    HistoryBillAdapter billAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_bill);
        this.getSupportActionBar().setTitle("Lịch sử hoá đơn");
        rcvTheBill=findViewById(R.id.lvHistoryBill);
        rcvTheBill.setLayoutManager(new LinearLayoutManager(this));

        //load data
        FirebaseRecyclerOptions<TheBill> options=
                new FirebaseRecyclerOptions.Builder<TheBill>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("HistoryBill"), TheBill.class)
                        .build();

        billAdapter =new HistoryBillAdapter(options,HistoryBill.this);
        rcvTheBill.setAdapter(billAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        billAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        billAdapter.startListening();
    }
}
