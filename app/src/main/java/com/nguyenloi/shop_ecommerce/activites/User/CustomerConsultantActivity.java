package com.nguyenloi.shop_ecommerce.activites.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.Class.CustomerConslutant;
import com.nguyenloi.shop_ecommerce.adapters.TheCustomerConslutantAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomerConsultantActivity extends AppCompatActivity {
    RecyclerView rcvQuestion;
    List<CustomerConslutant> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_consultant);
        this.getSupportActionBar().setTitle("Hỗ trợ");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setControl();
        initData();
        setRecyclerView();
    }
    private void setRecyclerView() {
        TheCustomerConslutantAdapter conslutantAdapter = new TheCustomerConslutantAdapter(list);
        rcvQuestion.setAdapter(conslutantAdapter);
        rcvQuestion.setHasFixedSize(true);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new CustomerConslutant(getString(R.string.question1),getString(R.string.answer1)));
        list.add(new CustomerConslutant(getString(R.string.question2),getString(R.string.answer2)));
        list.add(new CustomerConslutant(getString(R.string.question3),getString(R.string.answer3)));
        list.add(new CustomerConslutant(getString(R.string.question4),getString(R.string.answer4)));
        list.add(new CustomerConslutant(getString(R.string.question5),getString(R.string.answer5)));
        list.add(new CustomerConslutant(getString(R.string.question6),getString(R.string.answer6)));
        list.add(new CustomerConslutant(getString(R.string.question7),getString(R.string.answer7)));
        list.add(new CustomerConslutant(getString(R.string.question8),getString(R.string.answer8)));
        list.add(new CustomerConslutant(getString(R.string.question9),getString(R.string.answer9)));
        list.add(new CustomerConslutant(getString(R.string.question10),getString(R.string.answer10)));

    }


    private void setControl() {
       rcvQuestion=findViewById(R.id.rcvQuestion);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}