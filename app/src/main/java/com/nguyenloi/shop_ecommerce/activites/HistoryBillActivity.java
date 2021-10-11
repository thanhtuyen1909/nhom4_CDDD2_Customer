package com.nguyenloi.shop_ecommerce.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.TheBill;
import com.nguyenloi.shop_ecommerce.adapters.TheBillAdapter;

public class HistoryBillActivity extends AppCompatActivity {

    ListView lvHistoryBill;
    TheBillAdapter theBillAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_bill);
        this.getSupportActionBar().setTitle("Lịch sử hoá đơn");
        setControl();
        addData();
        lvHistoryBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HistoryBillActivity.this, TheBillHistoryDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fliter,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itFilterWaitInformation:
                Toast.makeText(HistoryBillActivity.this, "Chờ xác nhận", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itFilterConfirmed:
                Toast.makeText(HistoryBillActivity.this, "Đã xác nhận", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itFilterBeingTransported:
                Toast.makeText(HistoryBillActivity.this, "Đang vận chuyển", Toast.LENGTH_SHORT).show();
                break;
            case R.id.itFilterReceived:
                Toast.makeText(HistoryBillActivity.this, "Đã nhận hàng", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addData() {

        theBillAdapter.add(new TheBill("Đã nhận hàng",
                "22/10/2021","23/12/2022"));
        theBillAdapter.add(new TheBill("Đã xác nhận",
                " 12/10/2021","23/12/2022"));
        theBillAdapter.add(new TheBill("Đã thanh toán",
                "3/10/2021","23/12/2022"));
        theBillAdapter.add(new TheBill("Đang vân chuyển",
                "2/1/2024","23/12/2022"));
    }

    private void setControl() {
        lvHistoryBill=findViewById(R.id.lvHistoryBill);
        theBillAdapter=new TheBillAdapter(HistoryBillActivity.this,R.layout.item_bill);
        lvHistoryBill.setAdapter(theBillAdapter);
    }
}