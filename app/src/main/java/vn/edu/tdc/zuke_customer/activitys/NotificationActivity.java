package vn.edu.tdc.zuke_customer.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import vn.edu.tdc.zuke_customer.R;
import vn.edu.tdc.zuke_customer.adapters.NotificationAdapter;
import vn.edu.tdc.zuke_customer.data_models.Notification;

public class NotificationActivity extends AppCompatActivity {
    String accountID = "";
    RecyclerView recycleView;
    Toolbar toolbar;
    TextView subtitleAppbar;
    Intent intent;

    ArrayList<Notification> listNotify;
    NotificationAdapter notificationAdapter;
    DatabaseReference notiRef = FirebaseDatabase.getInstance().getReference("Notification");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list);

        // Nhận dữ liệu từ intent:
        intent = getIntent();
        accountID = intent.getStringExtra("accountID");

        // Toolbar:
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        subtitleAppbar = findViewById(R.id.subtitleAppbar);
        subtitleAppbar.setText(R.string.titleTB);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Khởi tạo biến:
        recycleView = findViewById(R.id.list);
        recycleView.setHasFixedSize(true);
        listNotify = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(this, listNotify);
        notificationAdapter.setItemClickListener(itemClickListener);
        data();
        recycleView.setAdapter(notificationAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        // Trượt xoá thông báo:
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recycleView.addItemDecoration(itemDecoration);
    }

    private final NotificationAdapter.ItemClickListener itemClickListener = new NotificationAdapter.ItemClickListener() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void delete(String id) {
            notiRef.child(id).removeValue();
            notificationAdapter.notifyDataSetChanged();
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void changeStatus(String id) {
            notiRef.child(id).child("status").setValue(1);
            notificationAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void data() {
        notiRef.orderByChild("created_at").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listNotify.clear();
                for (DataSnapshot node : snapshot.getChildren()) {
                    Notification noti = node.getValue(Notification.class);
                    noti.setKey(node.getKey());
                    if (noti.getAccountID().equals(accountID)) {
                        listNotify.add(noti);
                    }
                }
                Collections.reverse(listNotify);
                notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
