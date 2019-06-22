package com.example.aurigaaristowijaya.birthday;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewFriend extends AppCompatActivity implements View.OnClickListener{
    private TextView nama, telp, dob, email, address;
    private CircleImageView foto;
    private Button call, sms, emailer, wa;

    private String name, phone, mail, add, photo, birth;
    public int idi;
    private String id;

    private String msgTemp, msgSubject;
    private FriendData fd = new FriendData();
    private SettingsData sd = new SettingsData();
    private String u;
    private ArrayList<Friend> friend = new ArrayList<>();

    private LinearLayout chi, west;
    private TextView chinese, western;
    private String chiZod, westZod;

    private DatabaseReference mDatabase;
    private StorageReference mStorage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_friend);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        u = getIntent().getStringExtra("u"); //user ID

        nama = (TextView)findViewById(R.id.view_name);
        telp = (TextView)findViewById(R.id.view_phone);
        dob = (TextView)findViewById(R.id.view_date);
        email = (TextView)findViewById(R.id.view_email);
        address = (TextView)findViewById(R.id.view_address);
        foto = (CircleImageView) findViewById(R.id.view_photo);
        call = (Button)findViewById(R.id.view_call);
        call.setOnClickListener(this);
        sms = (Button)findViewById(R.id.view_sms);
        sms.setOnClickListener(this);
        emailer = (Button)findViewById(R.id.view_mail);
        emailer.setOnClickListener(this);
        wa = (Button)findViewById(R.id.view_wa);
        wa.setOnClickListener(this);

        chi = (LinearLayout)findViewById(R.id.view_chi);
        chinese = (TextView)findViewById(R.id.view_chinese);
        west = (LinearLayout)findViewById(R.id.view_zod);
        western = (TextView)findViewById(R.id.view_western);

        id = getIntent().getStringExtra("id");
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSettings(u);
        getData(u);
    }

    public boolean convertData(String ids){
        Friend f = fd.getFriend(ids);
        if (f == null){
            return false;
        }
        name = f.getName();
        phone = f.getPhone();
        birth = f.getDoB();
        mail = f.getEmail();
        add = f.getAddress();
        photo = f.getPhoto();

        setActionBarTitle(name);
        nama.setText(name);
        dob.setText(birth);
        email.setText(mail);
        telp.setText(phone);
        address.setText(add);

        mStorage = FirebaseStorage.getInstance().getReference().child(photo);
        mStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(ViewFriend.this)
                        .load(uri)
                        .crossFade()
                        .into(foto);
            }
        });

        return true;
    }

    private void loadZodiac(){
        if (sd.getChinese()){
            chi.setVisibility(View.VISIBLE);
        } else {
            chi.setVisibility(View.GONE);
        }

        if (sd.getWestern()){
            west.setVisibility(View.VISIBLE);
        } else {
            west.setVisibility(View.GONE);
        }

        generateZodiac();
    }

    private void generateZodiac(){
        String[] date = birth.split("/");
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);

        if ((month == 12 && day >= 22 && day <= 31) || (month ==  1 && day >= 1 && day <= 19))
            westZod = "Capricorn";
        else if ((month ==  1 && day >= 20 && day <= 31) || (month ==  2 && day >= 1 && day <= 17))
            westZod = "Aquarius";
        else if ((month ==  2 && day >= 18 && day <= 29) || (month ==  3 && day >= 1 && day <= 19))
            westZod = "Pisces";
        else if ((month ==  3 && day >= 20 && day <= 31) || (month ==  4 && day >= 1 && day <= 19))
            westZod = "Aries";
        else if ((month ==  4 && day >= 20 && day <= 30) || (month ==  5 && day >= 1 && day <= 20))
            westZod = "Taurus";
        else if ((month ==  5 && day >= 21 && day <= 31) || (month ==  6 && day >= 1 && day <= 20))
            westZod = "Gemini";
        else if ((month ==  6 && day >= 21 && day <= 30) || (month ==  7 && day >= 1 && day <= 22))
            westZod = "Cancer";
        else if ((month ==  7 && day >= 23 && day <= 31) || (month ==  8 && day >= 1 && day <= 22))
            westZod = "Leo";
        else if ((month ==  8 && day >= 23 && day <= 31) || (month ==  9 && day >= 1 && day <= 22))
            westZod = "Virgo";
        else if ((month ==  9 && day >= 23 && day <= 30) || (month == 10 && day >= 1 && day <= 22))
            westZod = "Libra";
        else if ((month == 10 && day >= 23 && day <= 31) || (month == 11 && day >= 1 && day <= 21))
            westZod = "Scorpio";
        else if ((month == 11 && day >= 22 && day <= 30) || (month == 12 && day >= 1 && day <= 21))
            westZod = "Sagittarius";

        switch (year % 12){
            case 0: chiZod = getResources().getString(R.string.monkey); break;
            case 1: chiZod = getResources().getString(R.string.rooster); break;
            case 2: chiZod = getResources().getString(R.string.dog); break;
            case 3: chiZod = getResources().getString(R.string.pig); break;
            case 4: chiZod = getResources().getString(R.string.rat); break;
            case 5: chiZod = getResources().getString(R.string.ox); break;
            case 6: chiZod = getResources().getString(R.string.tiger); break;
            case 7: chiZod = getResources().getString(R.string.rabbit); break;
            case 8: chiZod = getResources().getString(R.string.dragon); break;
            case 9: chiZod = getResources().getString(R.string.snake); break;
            case 10: chiZod = getResources().getString(R.string.horse); break;
            case 11: chiZod = getResources().getString(R.string.sheep); break;
        }

        western.setText(westZod);
        chinese.setText(chiZod);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.view_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.edit_friend:
//                mDatabase.child("user").child(u).child("settings").setValue(sd);
                Intent edit = new Intent(ViewFriend.this, AddNewFriend.class);
                edit.putExtra("id", id);
                edit.putExtra("u", u);
                startActivity(edit);
                break;
            case R.id.delete_friend:
                mDatabase.child("user").child(u).child("friendlist").child(id).removeValue();
                removeImage(photo);
//                mDatabase.child("user").child(u).child("settings").setValue(sd);
                Intent back = new Intent(ViewFriend.this, MainActivity.class);
                back.putExtra("u", u);
                startActivity(back);
                break;
            case R.id.friend_list:
//                mDatabase.child("user").child(u).child("settings").setValue(sd);
                Intent fl = new Intent(ViewFriend.this, MainActivity.class);
                fl.putExtra("u", u);
                startActivity(fl);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.view_call:
                Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(call);
                break;
            case R.id.view_sms:
                Intent sms = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
                sms.putExtra("address", phone);
                sms.putExtra("sms_body", msgTemp);
                startActivity(sms);
                break;
            case R.id.view_mail:
                Intent mailer = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + mail));
                mailer.putExtra(Intent.EXTRA_SUBJECT, msgSubject);
                mailer.putExtra(Intent.EXTRA_TEXT, msgTemp);
                startActivity(mailer);
                break;
            case R.id.view_wa:
                PackageManager pm = getPackageManager();
                try{
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_TEXT, msgTemp);
                    startActivity(Intent.createChooser(waIntent, getString(R.string.share_with)));
                } catch(PackageManager.NameNotFoundException e){
                    Toast.makeText(this, getString(R.string.wa_not_installed), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    public void getSettings(String u) {
        SettingsData data = new SettingsData();
        final SettingsData[] simpan = new SettingsData[1];
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
        msgTemp = sd.getMessageTemp();
        msgSubject = sd.getMessageSubject();
    }

    private void getData(String u) {
        mDatabase.child("user").child(u).child("friendlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datSnapshot : dataSnapshot.getChildren()) {
                    Friend f = datSnapshot.getValue(Friend.class);
                    friend.add(f);
                }

                FriendData.setData(friend);
                if (!convertData(id)){
                    Toast.makeText(ViewFriend.this, getString(R.string.load_data_failed), Toast.LENGTH_SHORT).show();
                } else {
                    loadZodiac();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void removeImage(String url) {
        StorageReference del = FirebaseStorage.getInstance().getReference().child(url);
        del.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ViewFriend.this, getString(R.string.friend_removed), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
