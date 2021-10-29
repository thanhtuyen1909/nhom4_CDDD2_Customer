package vn.edu.tdc.zuke_customer.activites.User;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import vn.edu.tdc.zuke_customer.Class.GlobalIdUser;
import vn.edu.tdc.zuke_customer.R;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class InformationUserActivity extends AppCompatActivity {
    Button btnInformationChangePassword, btnInformationSave;
    ImageView imgInformationEdit;
    CircleImageView imgInformation;
    EditText edtInformationUsername, edtMonth, edtDate, edtYear;
    TextView tvInformationPhone;

    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;


    Uri imageUri;
    boolean imageControl = false;
    String dateOfBirth = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_user);
        this.getSupportActionBar().setTitle("Thiết lập tài khoản");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                intent.putExtra("password", GlobalIdUser.getPassword());
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

    public void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    private void updateProfile() {
        if (!edtMonth.getText().equals("") && !edtYear.getText().equals("") && !edtDate.getText().equals("")) {
            if (edtYear.length() == 4 && edtDate.length() <= 2 && edtMonth.length() <= 2) {
                String strDate = edtYear.getText() + "-" + edtMonth.getText() + "-" + edtDate.getText();
                updateUserNameImage();
                reference.child("Customer").child(GlobalIdUser.customerId).child("dob").setValue(strDate);
            } else {
                Toast.makeText(InformationUserActivity.this, "Định dạng ngày sinh không phù hợp (ngày/thángg/năm)", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(InformationUserActivity.this, "Ngày sinh không được để trống", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUserNameImage() {
        String username = edtInformationUsername.getText().toString();
        reference.child("Customer").child(GlobalIdUser.customerId).child("name").setValue(username);


        if (imageControl) {
            UUID randomId = UUID.randomUUID();
            final String imageName = "images/profile/" + randomId + "jpg";
            storageReference.child(imageName).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    StorageReference myStorageRef = firebaseStorage.getReference(imageName);
                    myStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String filePath = uri.toString();
                            reference.child("Customer").child(GlobalIdUser.getCustomerId()).child("image").setValue(filePath)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            edtInformationUsername.setEnabled(false);
                                            Toast.makeText(InformationUserActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(InformationUserActivity.this, "Chỉnh sửa thông tin thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });
        }

    }

    private void loadDataUserFromUserId() {
        reference.child("Customer").child(GlobalIdUser.customerId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                String userName = task.getResult().child("name").getValue().toString();
                dateOfBirth = task.getResult().child("dob").getValue().toString();
                String url = task.getResult().child("image").getValue().toString();

                edtInformationUsername.setText(userName);
                tvInformationPhone.setText("Số điện thoại: " + GlobalIdUser.getPhoneNumber());
                if (url.equals("null")) {
                    imgInformation.setImageResource(R.drawable.pika);
                } else {
                    Picasso.get().load(url).into(imgInformation);
                }

                if (!dateOfBirth.equals("null")) {
                    edtDate.setEnabled(false);
                    edtMonth.setEnabled(false);
                    edtYear.setEnabled(false);
                    String[] partsDate = dateOfBirth.split("-");
                    edtDate.setText(partsDate[2]);
                    edtMonth.setText(partsDate[1]);
                    edtYear.setText(partsDate[0]);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imgInformation.setBackground(null);
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imgInformation);
            imageControl = true;
        } else {
            imageControl = false;
        }
    }


    private void setControl() {
        btnInformationChangePassword = findViewById(R.id.btnInformationChangePassword);
        btnInformationSave = findViewById(R.id.btnInformationSave);
        imgInformationEdit = findViewById(R.id.imgInformationEdit);
        tvInformationPhone = findViewById(R.id.tvInformationPhone);
        edtInformationUsername = findViewById(R.id.edtInformationUsername);
        imgInformation = findViewById(R.id.imgInformation);
        edtDate = findViewById(R.id.edtDate);
        edtMonth = findViewById(R.id.edtMonth);
        edtYear = findViewById(R.id.edtYear);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}