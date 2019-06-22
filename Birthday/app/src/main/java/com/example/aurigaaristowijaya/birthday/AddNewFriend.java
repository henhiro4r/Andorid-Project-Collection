package com.example.aurigaaristowijaya.birthday;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddNewFriend extends AppCompatActivity implements View.OnClickListener {
    private FriendData fd = new FriendData();
    private SettingsData sd = new SettingsData();
    private ArrayList<Friend> friend = new ArrayList<>();

    private EditText nama, email, telp, dob, alamat;
    private CircleImageView foto;
    private Button save, cancel, pickDate;
    private String img, url;
    private Uri pickedImage;

    public static String id = "id kosongan";
    private String idi;
    private int infobutton = 0;
    private int tanggal, bulan, tahun;
    private String u;
    boolean upload = false;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    int PReqCode = 1;
    int REQUESCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);
        setActionBarTitle(getString(R.string.add_friend));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        u = getIntent().getStringExtra("u"); //user ID

        nama = (EditText) findViewById(R.id.add_name);
        foto = (CircleImageView) findViewById(R.id.add_photo);
        foto.setOnClickListener(this);
        dob = (EditText) findViewById(R.id.add_date);
        dob.setEnabled(false);
        pickDate = (Button) findViewById(R.id.pick_date);
        pickDate.setOnClickListener(this);
        alamat = (EditText) findViewById(R.id.add_address);
        email = (EditText) findViewById(R.id.add_email);
        telp = (EditText) findViewById(R.id.add_phone);
        save = (Button) findViewById(R.id.add_save);
        save.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.add_cancel);
        cancel.setOnClickListener(this);

        idi = getIntent().getStringExtra("id"); //friend ID
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSettings(u);
        getData(u);
    }

    public void editData(String idi) {
        Friend f = fd.getFriend(idi);
        nama.setText(f.getName());
        dob.setText(f.getDoB());
        alamat.setText(f.getAddress());
        email.setText(f.getEmail());
        telp.setText(f.getPhone());

        url = f.getPhoto();
        mStorage = FirebaseStorage.getInstance().getReference().child(url);
        mStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(AddNewFriend.this)
                        .load(uri)
                        .crossFade()
                        .into(foto);
            }
        });

        save.setText(getString(R.string.edit));
    }

    public boolean convertFriend() {
        if (nama.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.name_req), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dob.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.date_req), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (telp.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.phone_req), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.getText().toString().isEmpty()) {
            email.setText("");
        }
        if (alamat.getText().toString().isEmpty()) {
            alamat.setText("");
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_photo:
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestForPermission();
                } else {
                    openGallery();
                }
                break;
            case R.id.add_save:
//                mDatabase.child("user").child(u).child("settings").setValue(sd);
                if (convertFriend()) {
                    if (idi.equals("new")) {
                        if (save()) {
                            Intent i = new Intent(AddNewFriend.this, MainActivity.class);
                            i.putExtra("u", u);
                            startActivity(i);
                        } else {
                            Toast.makeText(this, getString(R.string.failed_add_friend), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (update()) {
                            Intent i = new Intent(AddNewFriend.this, MainActivity.class);
                            i.putExtra("u", u);
                            startActivity(i);
                        } else {
                            Toast.makeText(this, getString(R.string.failed_edit_friend), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.add_cancel:
//                mDatabase.child("user").child(u).child("settings").setValue(sd);
                Intent i = new Intent(AddNewFriend.this, MainActivity.class);
                i.putExtra("u", u);
                startActivity(i);
                break;
            case R.id.pick_date:
                if (infobutton == 2) {
                    Friend fdate = fd.getFriend(idi);
                    String[] fdob = fdate.getDoB().toString().split("/");
                    tanggal = Integer.parseInt(fdob[0]);
                    bulan = Integer.parseInt(fdob[1]);
                    tahun = Integer.parseInt(fdob[2]);
                } else {
                    Calendar now = Calendar.getInstance();
                    tanggal = now.get(Calendar.DAY_OF_MONTH);
                    bulan = now.get(Calendar.MONTH);
                    tahun = now.get(Calendar.YEAR);
                }

                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, tahun, bulan, tanggal);
                datePickerDialog.setTitle(getString(R.string.dob));
                datePickerDialog.setCancelable(true);
                datePickerDialog.show();
                break;
        }
    }

    private boolean update() {
        String name = nama.getText().toString();
        String mail = email.getText().toString();
        String phone = telp.getText().toString();
        String date = dob.getText().toString();
        String add = alamat.getText().toString();

        if (u.isEmpty()) {
            return false;
        } else {
            Friend f;
            if (pickedImage != null) {
                f = new Friend(name, mail, phone, date, add, img, idi);
            } else {
                f = new Friend(name, mail, phone, date, add, url, idi);
            }
            Map<String, Object> dataupdate = f.toMap();
            Map<String, Object> updates = new HashMap<>();
            updates.put(idi, dataupdate);

            if (upload == true && pickedImage != null) {
                mDatabase.child("user").child(u).child("friendlist").updateChildren(updates);
                Toast.makeText(this, getString(R.string.friend_updated), Toast.LENGTH_SHORT).show();
                return true;
            } else if (upload == false && pickedImage == null) {
                mDatabase.child("user").child(u).child("friendlist").updateChildren(updates);
                Toast.makeText(this, getString(R.string.friend_updated), Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(this, getString(R.string.uploading_photos), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private boolean save() {
        String id = mDatabase.push().getKey();
        String name = nama.getText().toString();
        String mail = email.getText().toString();
        String phone = telp.getText().toString();
        String date = dob.getText().toString();
        String add = alamat.getText().toString();

        if (u == null) {
            Toast.makeText(this, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (pickedImage != null) {
                Friend f = new Friend(name, mail, phone, date, add, img, id);
                mDatabase.child("user").child(u).child("friendlist").child(id).setValue(f);
                Toast.makeText(this, getString(R.string.friend_added), Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(this, getString(R.string.pls_select_photo), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void getSettings(String u) {
        mDatabase.child("user").child(u).child("settings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SettingsData sd = dataSnapshot.getValue(SettingsData.class);
                loadSettings(sd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadSettings(SettingsData s){
        this.sd = s;
    }

    private void getData(String u){
        mDatabase.child("user").child(u).child("friendlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datSnapshot : dataSnapshot.getChildren()) {
                    Friend f = datSnapshot.getValue(Friend.class);
                    friend.add(f);
                }
                FriendData.setData(friend);

                if (!idi.equals("new")) {
                    idi = getIntent().getStringExtra("id");
                    infobutton = 2;
                    editData(idi);
                    setActionBarTitle(getString(R.string.edit_friend));
                } else {
                    save.setText(getString(R.string.save));
                    infobutton = 1;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(AddNewFriend.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(AddNewFriend.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(AddNewFriend.this, "Please allow to access gallery", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(AddNewFriend.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PReqCode);
            }
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, REQUESCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null) {
            pickedImage = data.getData();
            foto.setImageURI(pickedImage);
            //upload photo
            StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("friend_photos");
            final StorageReference image_path = mStorage.child(pickedImage.getLastPathSegment());
            image_path.putFile(pickedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    image_path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            img = "friend_photos/" + taskSnapshot.getMetadata().getName();
                            upload = true;
                        }
                    });
                }
            });
        }
    }
}
