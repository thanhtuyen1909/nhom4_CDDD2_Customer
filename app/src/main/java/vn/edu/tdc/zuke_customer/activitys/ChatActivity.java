package vn.edu.tdc.zuke_customer.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vn.edu.tdc.zuke_customer.R;
import vn.edu.tdc.zuke_customer.adapters.MessageAdapter;
import vn.edu.tdc.zuke_customer.data_models.Chat;

public class ChatActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtContent;
    ImageButton btnSend, btnAttach;
    Intent intent;
    String accountID = "", accountTVV = "AccountTTV";
    RecyclerView recyclerView;
    ArrayList<Chat> listMessage;
    MessageAdapter adapter;

    DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chats");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chat);

        // Lấy dữ liệu được gửi sang:
        intent = getIntent();
        accountID = intent.getStringExtra("accountID");

        // Toolbar:
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Khởi tạo biến:
        btnSend = findViewById(R.id.btnSend);
        btnAttach = findViewById(R.id.btnAttach);
        edtContent = findViewById(R.id.edtContent);
        recyclerView = findViewById(R.id.listMessage);

        // Xử lý sự kiện btnSend:
        btnSend.setOnClickListener(v -> {
            if (!String.valueOf(edtContent.getText()).isEmpty()) {
                String mess = edtContent.getText() + "";
                sendMessage(mess);
            }
        });

        // RecycleView
        recyclerView.setHasFixedSize(true);
        listMessage = new ArrayList<>();
        adapter = new MessageAdapter(this, listMessage, accountID);
        data();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void data() {
        chatRef.orderByChild("created_at").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMessage.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Chat chat = data.getValue(Chat.class);
                    if(chat.getSendID().equals(accountID) || chat.getReceiveID().equals(accountID)) listMessage.add(chat);
                }
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String mess) {
        // Format ngày tạo rating
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();

        Chat chat = new Chat();
        chat.setMessage(mess);
        chat.setSendID(accountID);
        chat.setReceiveID(accountTVV);
        chat.setCreated_at(sdf.format(date));

        chatRef.push().setValue(chat);
        edtContent.setText("");
    }
}
