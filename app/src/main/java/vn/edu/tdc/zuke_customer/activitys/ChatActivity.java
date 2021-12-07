package vn.edu.tdc.zuke_customer.activitys;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    String accountID = "", accountTVV = "";
    RecyclerView recyclerView;
    ArrayList<Chat> listMessage;
    MessageAdapter adapter;

    DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chats");
    DatabaseReference accRef = FirebaseDatabase.getInstance().getReference("Account");
    ValueEventListener seenListener;

    //permissions constants
    //image pick constants
    private static final int IMAGE_PICK_GALLERY_CODE = 4;
    Uri uri = null;

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
                sendMessage(mess, "text");
            }
        });

        btnAttach.setOnClickListener(view -> {
            if (!checkPermissionFromDevice()) {
                openGallery();
            } else {
                requestPermission();
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

        seenMessage();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void openGallery() {
        intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK_GALLERY_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == IMAGE_PICK_GALLERY_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private boolean checkPermissionFromDevice() {
        int write_external_strorage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return write_external_strorage_result == PackageManager.PERMISSION_DENIED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, IMAGE_PICK_GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK_GALLERY_CODE
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            uri = data.getData();

            //uploadToFirebase();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                reviewImage(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void reviewImage(Bitmap bitmap) {
        new DialogReviewSendImage(ChatActivity.this, bitmap).show(() -> {
            // to Upload Image to firebase storage to get url image...
            if (uri != null) {
                try {
                    sendImageMessage(uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void sendImageMessage(Uri uri) throws IOException {
        //progress dialog
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui lòng đợi... ");
        progressDialog.show();

        String fileNamePath = "ChatImages/post_" + System.currentTimeMillis();
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray(); //conver image to bytes
        StorageReference ref = FirebaseStorage.getInstance().getReference().child(fileNamePath);
        ref.putBytes(data)
                .addOnSuccessListener(taskSnapshot -> {
                    //image uploaded
                    progressDialog.dismiss();
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    String downloadUri = uriTask.getResult().toString();
                    if (uriTask.isSuccessful()) {
                        sendMessage(downloadUri, "image");
                    }
                })
                .addOnFailureListener(e -> progressDialog.dismiss());

    }

    private void seenMessage() {
        seenListener = chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if (chat.getReceiveID().equals(accountID) && chat.getSendID().equals(accountTVV)) {
                        dataSnapshot.getRef().child("isSeen").setValue(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void data() {
        chatRef.orderByChild("created_at").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMessage.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Chat chat = data.getValue(Chat.class);
                    if (chat.getSendID().equals(accountID) || chat.getReceiveID().equals(accountID))
                        listMessage.add(chat);
                }
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // lay accountTVV:
        accRef.orderByChild("role_id").equalTo(7).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot node : snapshot.getChildren()) {
                    accountTVV = node.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage(String mess, String type) {
        // Format ngày tạo rating
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();

        Chat chat = new Chat();
        chat.setMessage(mess);
        chat.setSendID(accountID);
        chat.setReceiveID(accountTVV);
        chat.setCreated_at(sdf.format(date));
        chat.setType(type);
        chat.setIsSeen(false);

        chatRef.push().setValue(chat);
        edtContent.setText("");
    }

    @Override
    protected void onPause() {
        chatRef.removeEventListener(seenListener);
        super.onPause();
    }
}
