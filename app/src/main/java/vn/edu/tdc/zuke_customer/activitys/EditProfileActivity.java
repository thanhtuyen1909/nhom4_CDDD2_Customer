package vn.edu.tdc.zuke_customer.activitys;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import vn.edu.tdc.zuke_customer.R;
import vn.edu.tdc.zuke_customer.data_models.Customer;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    TextView subtitleAppbar;
    String accountID = "";
    Intent intent;
    TextView title, mess;
    ImageView imgCus, btnChooseDate;
    Button btnChangePass, btnSubmit;
    EditText edtDOB, edtName, edtEmail, edtPass;
    Handler handler = new Handler();
    private final int PICK_IMAGE_REQUEST = 1;
    Uri filePath = null;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    StorageReference storage = FirebaseStorage.getInstance().getReference();
    DatabaseReference cusRef = db.getReference("Customer");
    DatabaseReference accountRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_profile);

        // Nh???n d??? li???u t??? intent:
        intent = getIntent();
        if (intent != null) {
            accountID = intent.getStringExtra("accountID");
            accountRef = db.getReference("Account/" + accountID);
        }

        // Toolbar:
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        subtitleAppbar = findViewById(R.id.subtitleAppbar);
        subtitleAppbar.setText(R.string.titleCSTTCN);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Kh???i t???o bi???n:
        imgCus = findViewById(R.id.img);
        edtDOB = findViewById(R.id.edtDOB);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        btnChangePass = findViewById(R.id.buttonChangePass);
        btnSubmit = findViewById(R.id.buttonSubmit);
        btnChooseDate = findViewById(R.id.buttonChangeDate);

        // Set s??? ki???n cho c??c ?????i t?????ng:
        btnSubmit.setOnClickListener(this);
        imgCus.setOnClickListener(this);
        btnChangePass.setOnClickListener(this);
        btnChooseDate.setOnClickListener(this);

        // L???y kh??ch h??ng t????ng ???ng:
        cusRef.orderByChild("accountID").equalTo(accountID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Customer customer = snapshot1.getValue(Customer.class);
                    if (!customer.getImage().equals("")) {
                        Picasso.get().load(customer.getImage()).fit().into(imgCus);
                    } else imgCus.setImageResource(R.drawable.man);
                    edtDOB.setText(customer.getDob());
                    edtName.setText(customer.getName());
                    edtEmail.setText(customer.getEmail());
                    btnChooseDate.setVisibility(customer.getDob().equals("") ? View.VISIBLE : View.GONE);
                }
//https://firebasestorage.googleapis.com/v0/b/cddd2-f1bcd.appspot.com/o/images%2Fprofile%2Fcdffb4c5-f659-45c6-bd69-5cfbd8788847jpg?alt=media&token=1f3177ae-0182-4293-b418-89a3871f3e59
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            Picasso.get().load(filePath).into(imgCus);
        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, (view, year, month, dayOfMonth) -> {
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            edtDOB.setText(format1.format(format1.parse(dayOfMonth + "/" + (month + 1) + "/" + year, new ParsePosition(0))));
        },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        if (!edtDOB.getText().equals("")) {
            btnChooseDate.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == imgCus) {
            chooseImage();
        } else if (v == btnChangePass) {
            intent = new Intent(EditProfileActivity.this, ChangePasswordActivity.class);
            intent.putExtra("accountID", accountID);
            startActivity(intent);
        } else if (v == btnSubmit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this, R.style.AlertDialogTheme);
            View view = LayoutInflater.from(EditProfileActivity.this).inflate(
                    R.layout.text_input_pass,
                    findViewById(R.id.layoutDialogContainer)
            );
            builder.setView(view);
            title = view.findViewById(R.id.textTitle);
            title.setText(R.string.title);
            edtPass = view.findViewById(R.id.edtPass);
            ((TextView) view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.yes));

            final AlertDialog alertDialog = builder.create();

            view.findViewById(R.id.buttonAction).setOnClickListener(v1 -> {
                // Ki???m tra v???i pass ???? c?? ????ng trong account hay k?
                accountRef.child("password").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // N???u ????ng th?? thay ?????i d??? li???u + th??ng b??o success -> ????ng dialog
                        if (snapshot.getValue(String.class).equals(edtPass.getText() + "")) {
                            if (filePath != null) {
                                UUID randomId = UUID.randomUUID();
                                final String imageName = "images/profile/" + randomId + "jpg";
                                storage.child(imageName).putFile(filePath).addOnSuccessListener(taskSnapshot -> {
                                    StorageReference myStorageRef = FirebaseStorage.getInstance().getReference(imageName);
                                    myStorageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                        cusRef.orderByChild("accountID").equalTo(accountID).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                                                    Customer customer = snapshot1.getValue(Customer.class);
                                                    customer.setDob(edtDOB.getText() + "");
                                                    customer.setName(edtName.getText() + "");
                                                    customer.setEmail(edtEmail.getText() + "");
                                                    String filePath = uri.toString();
                                                    customer.setImage(filePath);
                                                    cusRef.child(snapshot1.getKey()).setValue(customer).addOnSuccessListener(unused -> {
                                                        alertDialog.dismiss();
                                                        showSuccesDialog("C???p nh???t th??ng tin th??nh c??ng");
                                                    }).addOnFailureListener(e -> showWarningDialog("C???p nh???t th??ng tin th???t b???i!"));
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    });
                                });
                            }
                            else {
                                if (!edtName.getText().equals("")) {
                                    cusRef.orderByChild("accountID").equalTo(accountID).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                                                Customer customer = snapshot1.getValue(Customer.class);
                                                customer.setDob(edtDOB.getText() + "");
                                                customer.setName(edtName.getText() + "");
                                                customer.setEmail(edtEmail.getText() + "");
                                                cusRef.child(snapshot1.getKey()).setValue(customer).addOnSuccessListener(unused -> {
                                                    alertDialog.dismiss();
                                                    showSuccesDialog("C???p nh???t th??ng tin th??nh c??ng");
                                                }).addOnFailureListener(e -> showWarningDialog("C???p nh???t th??ng tin th???t b???i!"));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    alertDialog.dismiss();
                                } else showWarningDialog("T??n hi???n th??? kh??ng ???????c ????? tr???ng!");
                            }
                        }
                        // N???u sai th?? th??ng b??o warning
                        else {
                            showWarningDialog("Vui l??ng ki???m tra l???i m???t kh???u!");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            });


            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            alertDialog.show();
        } else {
            showDatePickerDialog();
        }
    }

    private void showSuccesDialog(String notify) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(EditProfileActivity.this).inflate(
                R.layout.layout_succes_dialog,
                findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        title = view.findViewById(R.id.textTitle);
        title.setText(R.string.title);
        mess = view.findViewById(R.id.textMessage);
        mess.setText(notify);
        ((TextView) view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.okay));

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setVisibility(View.GONE);

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

        handler.postDelayed(alertDialog::dismiss, 1500);
    }

    private void showWarningDialog(String notify) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(EditProfileActivity.this).inflate(
                R.layout.layout_warning_dialog,
                findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        title = view.findViewById(R.id.textTitle);
        title.setText(R.string.title);
        mess = view.findViewById(R.id.textMessage);
        mess.setText(notify);
        ((TextView) view.findViewById(R.id.buttonAction)).setText(getResources().getString(R.string.yes));

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonAction).setOnClickListener(v -> {
            alertDialog.dismiss();
        });


        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}
