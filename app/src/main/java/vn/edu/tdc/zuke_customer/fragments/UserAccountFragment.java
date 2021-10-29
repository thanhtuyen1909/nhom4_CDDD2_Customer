package vn.edu.tdc.zuke_customer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import vn.edu.tdc.zuke_customer.Class.GlobalIdUser;
import vn.edu.tdc.zuke_customer.R;
import vn.edu.tdc.zuke_customer.activites.Other.BottomNavigationUserActivity;
import vn.edu.tdc.zuke_customer.activites.User.CustomerConsultantActivity;
import vn.edu.tdc.zuke_customer.activites.User.InformationUserActivity;
import vn.edu.tdc.zuke_customer.activites.User.IntroduceActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserAccountFragment extends Fragment {
    Button btnAccountHistory, btnAccountIntroduce, btnAccountSetting, btnAccountSupport, btnAccountLogout;
    CircleImageView imgAccount;
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
                //set status when logout
                FirebaseDatabase.getInstance().getReference().child("Customer")
                        .child(GlobalIdUser.customerId).child("status").setValue("red");
                BottomNavigationUserActivity mainActivity = (BottomNavigationUserActivity) getActivity();
                mainActivity.takeLogout();
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