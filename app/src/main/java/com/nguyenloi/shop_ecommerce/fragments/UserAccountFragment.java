package com.nguyenloi.shop_ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.nguyenloi.shop_ecommerce.Class.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.activites.User.CustomerConsultantActivity;
import com.nguyenloi.shop_ecommerce.activites.Bill.HistoryBillActivity;
import com.nguyenloi.shop_ecommerce.activites.User.InformationUserActivity;
import com.nguyenloi.shop_ecommerce.activites.User.IntroduceActivity;
import com.squareup.picasso.Picasso;


public class UserAccountFragment extends Fragment {
    Button btnAccountHistory, btnAccountIntroduce, btnAccountSetting, btnAccountSupport, btnAccountLogout;
    ImageView imgAccount;
    TextView tvAccout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_account, container, false);

        setControl(view);
        //Load data
        loadDataCustomer();

        btnAccountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InformationUserActivity.class);
                startActivity(intent);
            }
        });
        btnAccountIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), IntroduceActivity.class);
                startActivity(intent);
            }
        });
        btnAccountLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnAccountSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CustomerConsultantActivity.class);
                startActivity(intent);
            }
        });
        btnAccountHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HistoryBillActivity.class);
                startActivity(intent);
            }
        });
        return view;



    }


    private void loadDataCustomer() {
        FirebaseDatabase.getInstance().getReference().child("Customer").child(GlobalIdUser.customerId)
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                String userName = task.getResult().child("name").getValue().toString();
                String url = task.getResult().child("image").getValue().toString();

                tvAccout.setText( userName);
                if (url.equals("null")) {
                    imgAccount.setImageResource(R.drawable.pika);
                } else {
                    Picasso.get().load(url).into(imgAccount);
                }

            }
        });
    }


    private void setControl(View view) {
        btnAccountHistory = view.findViewById(R.id.btnAccountHistory);
        btnAccountIntroduce = view.findViewById(R.id.btnAccountIntroduce);
        btnAccountLogout = view.findViewById(R.id.btnAccountLogout);
        btnAccountSupport = view.findViewById(R.id.btnAccountSupport);
        btnAccountSetting = view.findViewById(R.id.btnAccountSetting);
        tvAccout = view.findViewById(R.id.tvAccount);
        imgAccount = view.findViewById(R.id.imgAccount);

    }
}
