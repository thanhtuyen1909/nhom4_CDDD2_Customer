package com.nguyenloi.shop_ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nguyenloi.shop_ecommerce.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.activites.BottomNavigationUserActivity;
import com.nguyenloi.shop_ecommerce.activites.CustomerConsultantActivity;
import com.nguyenloi.shop_ecommerce.activites.HistoryBillActivity;
import com.nguyenloi.shop_ecommerce.activites.InformationUserActivity;
import com.nguyenloi.shop_ecommerce.activites.IntroduceActivity;
import com.nguyenloi.shop_ecommerce.activites.LoginActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserAccountFragment extends Fragment {
    Button btnAccountHistory, btnAccountIntroduce,btnAccountSetting,btnAccountSupport, btnAccountLogout;
    ImageView imgAccount;
    TextView tvAccout;

    FirebaseDatabase database ;
    DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_account,container,false);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        setControl(view);
        //Load data
        loadDataUserFromUserId();

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
//                Intent intent = new Intent(getContext(), IntroduceActivity.class);
//                startActivity(intent);
                Toast.makeText(getContext(), GlobalIdUser.userId, Toast.LENGTH_SHORT).show();
            }
        });
        btnAccountLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BottomNavigationUserActivity bottomNavigationActivity = (BottomNavigationUserActivity) getActivity();
//                bottomNavigationActivity.takeLogout("LogoutAccount");
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
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

    private void loadDataUserFromUserId(){
        reference.child("Account").child(GlobalIdUser.userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                String userName = task.getResult().child("username").getValue().toString();
                String url = task.getResult().child("image").getValue().toString();

                tvAccout.setText(userName);
                if(url.equals("null")){
                    imgAccount.setImageResource(R.drawable.pika);
                }else{
                    Picasso.get().load(url).into(imgAccount);
                }

            }
        });
    }

    private void setControl(View view) {
        btnAccountHistory=view.findViewById(R.id.btnAccountHistory);
        btnAccountIntroduce=view.findViewById(R.id.btnAccountIntroduce);
        btnAccountLogout=view.findViewById(R.id.btnAccountLogout);
        btnAccountSupport=view.findViewById(R.id.btnAccountSupport);
        btnAccountSetting=view.findViewById(R.id.btnAccountSetting);
        tvAccout=view.findViewById(R.id.tvAccount);
        imgAccount=view.findViewById(R.id.imgAccount);
    }
}
