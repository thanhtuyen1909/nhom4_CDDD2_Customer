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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.nguyenloi.shop_ecommerce.R;
import com.nguyenloi.shop_ecommerce.activites.BottomNavigationUserActivity;
import com.nguyenloi.shop_ecommerce.activites.CustomerConsultantActivity;
import com.nguyenloi.shop_ecommerce.activites.HistoryBillActivity;
import com.nguyenloi.shop_ecommerce.activites.InformationUserActivity;
import com.nguyenloi.shop_ecommerce.activites.IntroduceActivity;
import com.nguyenloi.shop_ecommerce.activites.LoginActivity;

public class UserAccountFragment extends Fragment {
    Button btnAccountHistory, btnAccountIntroduce,btnAccountSetting,btnAccountSupport, btnAccountLogout;
    ImageView imgAccount;
    TextView tvAccout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_account,container,false);

        setControl(view);
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
