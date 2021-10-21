package com.nguyenloi.shop_ecommerce.activites.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nguyenloi.shop_ecommerce.Class.GlobalIdUser;
import com.nguyenloi.shop_ecommerce.R;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class InformationUserActivity extends AppCompatActivity {
    Button btnInformationChangePassword,btnInformationSave;
    ImageView  imgInformationEdit, imgInformation;
    EditText edtInformationUsername;
    TextView tvInformationPhone;

    FirebaseDatabase database;
    DatabaseReference reference ;

    FirebaseStorage firebaseStorage ;
    StorageReference storageReference;

    Uri imageUri;
    boolean imageControl = false;

    String passwordUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_user);
        this.getSupportActionBar().setTitle("Thiết lập tài khoản");
        setControl();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        loadDataUserFromUserId();

        btnInformationChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationUserActivity.this, ChangePasswordActivity.class);
                intent.putExtra("password",passwordUser);
                startActivity(intent);
            }
        });

        //Set Enable icon
        imgInformationEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtInformationUsername.setEnabled(true);
            }
        });

        btnInformationSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        //Pick image
        imgInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }

    public void imageChooser (){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);

    }

    private void updateProfile(){
        String username = edtInformationUsername.getText().toString();
        reference.child("Account").child(GlobalIdUser.userId).child("username").setValue(username);


        if(imageControl){
            UUID randomId = UUID.randomUUID();
            final  String imageName = "images/"+randomId+"jpg";
            storageReference.child(imageName).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageReference myStorageRef = firebaseStorage.getReference(imageName);
                    myStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String filePath = uri.toString();
                            reference.child("Account").child(GlobalIdUser.userId).child("image").setValue(filePath)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(InformationUserActivity.this, "Chỉnh sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(InformationUserActivity.this,"Chỉnh sửa thông tin thất bại" , Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });
        }else{
            reference.child("Users").child(GlobalIdUser.userId).child("image").setValue("null");
        }

    }

    private void loadDataUserFromUserId(){
        reference.child("Account").child(GlobalIdUser.userId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                String userName = task.getResult().child("username").getValue().toString();
                String url = task.getResult().child("image").getValue().toString();
                String phone = task.getResult().child("phone").getValue().toString();
                passwordUser = task.getResult().child("password").getValue().toString();

                edtInformationUsername.setText(userName);
                tvInformationPhone.setText(phone);
                if(url.equals("null")){
                    imgInformation.setImageResource(R.drawable.pika);
                }else{
                    Picasso.get().load(url).into(imgInformation);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            imgInformation.setBackground(null);
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imgInformation);
            imageControl =  true;
        }
        else
        {
            imageControl = false;
        }
    }

    private void setControl() {
        btnInformationChangePassword=findViewById(R.id.btnInformationChangePassword);
        btnInformationSave=findViewById(R.id.btnInformationSave);
        imgInformationEdit=findViewById(R.id.imgInformationEdit);
        tvInformationPhone=findViewById(R.id.tvInformationPhone);
        edtInformationUsername=findViewById(R.id.edtInformationUsername);
        imgInformation=findViewById(R.id.imgInformation);
    }
}