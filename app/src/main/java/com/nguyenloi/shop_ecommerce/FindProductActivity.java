package com.nguyenloi.shop_ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import com.nguyenloi.shop_ecommerce.adapters.TheListAdapter;

public class FindProductActivity extends AppCompatActivity {
   ListView lvFindNameProduct;
   TheListAdapter theListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_product);
        lvFindNameProduct=findViewById(R.id.lvFindNameProduct);
        theListAdapter = new TheListAdapter(FindProductActivity.this,R.layout.item_list);
        lvFindNameProduct.setAdapter(theListAdapter);
        addData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_find_product,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void addData() {
        theListAdapter.add(new TheList(R.drawable.lap5,1000000,13,"Laptop Asus"));
        theListAdapter.add(new TheList(R.drawable.lap2,1200000,13,"Macbook pro"));
        theListAdapter.add(new TheList(R.drawable.lap3,1430000,13,"Dell i3"));
        theListAdapter.add(new TheList(R.drawable.lap4,1320000,13,"Lenovo"));
        theListAdapter.add(new TheList(R.drawable.lap5,9000000,13,"Asus i5"));
    }
}