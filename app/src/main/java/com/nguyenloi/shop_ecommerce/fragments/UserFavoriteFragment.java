package com.nguyenloi.shop_ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nguyenloi.shop_ecommerce.FindProductActivity;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.TheList;
import com.nguyenloi.shop_ecommerce.activites.CartActivity;
import com.nguyenloi.shop_ecommerce.activites.DetailProductActivity;
import com.nguyenloi.shop_ecommerce.adapters.TheListAdapter;

public class UserFavoriteFragment extends Fragment {
    TheListAdapter theListAdapter;
    ListView lvFavorite;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_user_favorite,container,false);
        setHasOptionsMenu(true);
        lvFavorite=view.findViewById(R.id.lvFavorite);
        theListAdapter = new TheListAdapter(this.getActivity(),R.layout.item_list);
        lvFavorite.setAdapter(theListAdapter);
        addData();
        lvFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent3 = new Intent(getContext(), FindProductActivity.class);
                startActivity(intent3);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itCart:
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
                break;
            case R.id.itNotification:
                Toast.makeText(getContext(), "Th??ng b??o", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    private void addData() {
        theListAdapter.add(new TheList(R.drawable.lap5,1000000,13,"Laptop Asus"));
        theListAdapter.add(new TheList(R.drawable.lap2,1200000,13,"Macbook pro"));
        theListAdapter.add(new TheList(R.drawable.lap3,1430000,13,"Dell i3"));
        theListAdapter.add(new TheList(R.drawable.lap4,1320000,13,"Lenovo"));
        theListAdapter.add(new TheList(R.drawable.lap5,9000000,13,"Asus i5"));
    }
}
