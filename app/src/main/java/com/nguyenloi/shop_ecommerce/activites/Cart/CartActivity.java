package com.nguyenloi.shop_ecommerce.activites.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.Class.TheCart;
import com.nguyenloi.shop_ecommerce.adapters.TheCartAdapter;

public class CartActivity extends AppCompatActivity {
    ListView lvCart;
    Button btnCartSubmit;
    TheCartAdapter theCartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        this.getSupportActionBar().setTitle("Giỏ hàng");
        setControl();
        addData();
        btnCartSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, CartDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itCartDelete:
                AlertDialog.Builder b = new AlertDialog.Builder(CartActivity.this);
                b.setTitle("Thông báo");
                b.setMessage("Bạn có muốn xoá toàn bộ giỏ hàng này không?");
                b.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                b.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog al = b.create();
                al.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cart,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void addData() {
        theCartAdapter.add(new TheCart(1200000,1, R.drawable.lap1, "ASUS"));
        theCartAdapter.add(new TheCart(1100000,3, R.drawable.lap1, "Lenovo"));
        theCartAdapter.add(new TheCart(800000,2, R.drawable.lap1, "Macbook"));
        theCartAdapter.add(new TheCart(3100000,4, R.drawable.lap1, "DeLL"));
    }

    private void setControl() {
        lvCart=findViewById(R.id.lvCart);
        btnCartSubmit=findViewById(R.id.btnCartSubmit);
        theCartAdapter = new TheCartAdapter(CartActivity.this, R.layout.item_cart);
        lvCart.setAdapter(theCartAdapter);
    }
}