package com.nguyenloi.shop_ecommerce.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.TheBillHistoryDetail;
import com.nguyenloi.shop_ecommerce.adapters.TheBillDetailAdapter;

public class TheBillHistoryDetailActivity extends AppCompatActivity {
    ListView lvHistoryBillDetail;
    TheBillDetailAdapter billDetailAdapter;
    TextView tvHistoryDetailAddress,tvHistoryDetailNote,tvHistoryDetailStatus,tvHistoryDetailBookingOrder,
            tvHistoryDetailReceived,tvHistoryDetailTotalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_bill_history_detail);
        this.getSupportActionBar().setTitle("Lịch sử hoá đơn");
        setControl();
        addData();
    }

    private void addData() {
        billDetailAdapter.add(new TheBillHistoryDetail(R.drawable.lap1, 3, 1300000, 39000000,"Asus"));
        billDetailAdapter.add(new TheBillHistoryDetail(R.drawable.lap2, 2, 1200000, 39000000,"Macbook"));
        billDetailAdapter.add(new TheBillHistoryDetail(R.drawable.lap3, 1, 1500000, 39000000,"Lenovo"));
        billDetailAdapter.add(new TheBillHistoryDetail(R.drawable.lap4, 3, 1100000, 39000000,"Dell"));
    }

    private void setControl() {
        lvHistoryBillDetail=findViewById(R.id.lvHistoryBillDetail);
        billDetailAdapter = new TheBillDetailAdapter(TheBillHistoryDetailActivity.this, R.layout.item_history_bill_detail);
        lvHistoryBillDetail.setAdapter(billDetailAdapter);

    }
}