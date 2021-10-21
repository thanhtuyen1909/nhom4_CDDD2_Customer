package com.nguyenloi.shop_ecommerce.activites.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.Class.TheCustomerConslutant;
import com.nguyenloi.shop_ecommerce.adapters.TheCustomerConslutantAdapter;

public class CustomerConsultantActivity extends AppCompatActivity {
    ListView lvQuestion;
    TheCustomerConslutantAdapter conslutantAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_consultant);
        this.getSupportActionBar().setTitle("Hỗ trợ");
        setControl();
        addData();
        lvQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TheCustomerConslutant conslutant = conslutantAdapter.getItem(position);
                AlertDialog.Builder b = new AlertDialog.Builder(CustomerConsultantActivity.this);
                b.setTitle("Trả lời");
                b.setMessage(conslutant.getAnswer());
                b.setPositiveButton("Đã hiểu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog al = b.create();
                al.show();
            }
        });
    }

    private void addData() {
        conslutantAdapter.add(new TheCustomerConslutant(getString(R.string.question1),getString(R.string.answer1)));
        conslutantAdapter.add(new TheCustomerConslutant(getString(R.string.question2),getString(R.string.answer2)));
        conslutantAdapter.add(new TheCustomerConslutant(getString(R.string.question3),getString(R.string.answer3)));
        conslutantAdapter.add(new TheCustomerConslutant(getString(R.string.question4),getString(R.string.answer4)));;
        conslutantAdapter.add(new TheCustomerConslutant(getString(R.string.question5),getString(R.string.answer5)));
        conslutantAdapter.add(new TheCustomerConslutant(getString(R.string.question6),getString(R.string.answer6)));;
        conslutantAdapter.add(new TheCustomerConslutant(getString(R.string.question7),getString(R.string.answer7)));
        conslutantAdapter.add(new TheCustomerConslutant(getString(R.string.question8),getString(R.string.answer8)));;
        conslutantAdapter.add(new TheCustomerConslutant(getString(R.string.question9),getString(R.string.answer9)));
        conslutantAdapter.add(new TheCustomerConslutant(getString(R.string.question10),getString(R.string.answer10)));;;
    }

    private void setControl() {
        lvQuestion=findViewById(R.id.lvQuestion);
        conslutantAdapter = new TheCustomerConslutantAdapter(CustomerConsultantActivity.this,R.layout.item_the_customer_conslutant);
        lvQuestion.setAdapter(conslutantAdapter);
    }
}