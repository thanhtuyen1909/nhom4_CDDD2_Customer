package vn.edu.tdc.zuke_customer.activites.Other;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import vn.edu.tdc.zuke_customer.R;
import vn.edu.tdc.zuke_customer.activites.Login.LoginActivity;
import vn.edu.tdc.zuke_customer.fragments.UserAccountFragment;
import vn.edu.tdc.zuke_customer.fragments.UserFavoriteFragment;
import vn.edu.tdc.zuke_customer.fragments.UserHomeFragment;
import vn.edu.tdc.zuke_customer.fragments.UserListFragment;

public class BottomNavigationUserActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_user);
        this.getSupportActionBar().hide();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserHomeFragment()).commit();
        bottomNavigationView=findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.itHome:
                        fragment = new UserHomeFragment();
                        break;
                    case R.id.itNotification:
                        fragment = new UserListFragment();
                        break;
                    case R.id.itFavorite:
                        fragment = new UserFavoriteFragment();
                        break;
                    case R.id.itAccount:
                        fragment = new UserAccountFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                return  true;
            }
        });

    }
    public void takeLogout(){
       Intent intent = new Intent(BottomNavigationUserActivity.this, LoginActivity.class);
       startActivity(intent);
       finish();
    }
}