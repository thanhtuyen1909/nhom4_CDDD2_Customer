package com.nguyenloi.shop_ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.nguyenloi.shop_ecommerce.adapters.NotificationAdapter;

public class NotificationActiviy extends AppCompatActivity {

    ListView lvNotification;
    NotificationAdapter notificationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_activiy);
        this.getSupportActionBar().setTitle("Thông báo");
        setControl();
        addData();
    }

    private void addData() {
        notificationAdapter.add(new Notification(R.drawable.no1,"Món hàng đang được chờ xác nhận","Bạn hãy kiểm tra lại thông tin nhận hàng. Chúng tôi sẽ cố gắng gửi đơn hàng đến cho bạn một cách sớm nhất"));
        notificationAdapter.add(new Notification(R.drawable.no2,"Iphone 11 64GB ProMax","Iphone 11 64 ProMax đang được khuyến mãi với giá 11.400.000 VNĐ trong vòng 14 ngày. Các bạn hãy tham gia ngay trong thời gian vì số lượng có hạn"));
        notificationAdapter.add(new Notification(R.drawable.no3,"Iphone 10 32GB ","Sắp hết thời gian khuyến mãi chỉ còn 3 ngày. Iphone 10 chỉ với giá 8.000.000 số lượng còn lại là 12 trong cửa hàng chúng tôi"));
    }

    private void setControl() {
        lvNotification=findViewById(R.id.lvNotification);
        notificationAdapter = new NotificationAdapter(NotificationActiviy.this,R.layout.item_notification);
        lvNotification.setAdapter(notificationAdapter);
    }
}