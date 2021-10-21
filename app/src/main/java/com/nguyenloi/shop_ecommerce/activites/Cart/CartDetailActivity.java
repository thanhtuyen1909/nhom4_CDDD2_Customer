package com.nguyenloi.shop_ecommerce.activites.Cart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.Class.TheBillHistoryDetail;
import com.nguyenloi.shop_ecommerce.adapters.TheBillDetailAdapter;

public class CartDetailActivity extends AppCompatActivity {
    ListView lvCartDetail;
    TheBillDetailAdapter billDetailAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);
        this.getSupportActionBar().setTitle("Thanh toán");
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
        lvCartDetail=findViewById(R.id.lvCartDetail);
        billDetailAdapter = new TheBillDetailAdapter(CartDetailActivity.this, R.layout.item_history_bill_detail);
        lvCartDetail.setAdapter(billDetailAdapter);

    }
}