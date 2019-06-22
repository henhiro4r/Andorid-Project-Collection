package com.example.aurigaaristowijaya.birthday;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.Locale;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    private SettingsData sd = new SettingsData();
    private FriendData fd = new FriendData();

    private LinearLayout msgTemp, zodiacSign;
    private TextView zodiacSignN;
    private String[] zodiac, lang;

    private TextView timeN, onBirthN, oneDayN, twoDayN, sevenDayN, fourteenDayN;
    private LinearLayout timeNotif;
    private CheckBox onBirthday, oneDay, twoDay, sevenDay, fourteenDay;
    private int nHour, nMinute;

    private LinearLayout about, language;
    private TextView langN;

    private LinearLayout restoreDef, facReset;

    private int defZodiacSign = 0, defLang = 0;
    private Locale locale;

    private String u;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        setActionBarTitle(getString(R.string.settings));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        u = getIntent().getStringExtra("u"); //user ID

        zodiacSign = (LinearLayout)findViewById(R.id.set_zodiac_signs);
        zodiacSign.setOnClickListener(this);
        zodiac = new String[]{getString(R.string.western), getString(R.string.chinese), getString(R.string.western_chinese), getString(R.string.none)};
        zodiacSignN = (TextView)findViewById(R.id.zodiac_signs);
        msgTemp = (LinearLayout) findViewById(R.id.messageTemp);
        msgTemp.setOnClickListener(this);

        timeNotif = (LinearLayout)findViewById(R.id.set_time_notif);
        timeNotif.setOnClickListener(this);
        timeN = (TextView)findViewById(R.id.time_notif);
        onBirthday = (CheckBox)findViewById(R.id.set_on_birthday);
        onBirthN = (TextView)findViewById(R.id.on_birthday);
        oneDay = (CheckBox)findViewById(R.id.set_one_day_before);
        oneDayN = (TextView)findViewById(R.id.one_day_before);
        twoDay = (CheckBox)findViewById(R.id.set_two_days_before);
        twoDayN = (TextView)findViewById(R.id.two_days_before);
        sevenDay = (CheckBox)findViewById(R.id.set_seven_days_before);
        sevenDayN = (TextView)findViewById(R.id.seven_days_before);
        fourteenDay = (CheckBox)findViewById(R.id.set_fourteen_days_before);
        fourteenDayN = (TextView)findViewById(R.id.fourteen_days_before);

        about = (LinearLayout)findViewById(R.id.about);
        language = (LinearLayout)findViewById(R.id.language);
        language.setOnClickListener(this);
        lang = new String[]{getString(R.string.english), getString(R.string.indonesia)};
        langN = (TextView)findViewById(R.id.lang);

        restoreDef = (LinearLayout)findViewById(R.id.restore_default);
        restoreDef.setOnClickListener(this);
        facReset = (LinearLayout)findViewById(R.id.factory_reset);
        facReset.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSettings(u);
    }

    @Override
    public void onClick(View v){
        if (v == zodiacSign){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.zodiac_signs);
            builder.setSingleChoiceItems(zodiac, defZodiacSign, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    zodiacSignN.setText(zodiac[which]);
                    sd.setZodiac(which);
                    zodiacSetter();
                    mDatabase.child("user").child(u).child("settings").child("zodiac").setValue(which);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (v == about){
            Intent about = new Intent(Settings.this, About.class);
            about.putExtra("u", u);
            startActivity(about);
        } else if (v == timeNotif){
            if (sd.getHour() == 0 && sd.getMinute() == 0){
                Calendar now = Calendar.getInstance();
                nHour = now.get(Calendar.HOUR_OF_DAY);
                nMinute = now.get(Calendar.MINUTE);
            } else {
                nHour = sd.getHour();
                nMinute = sd.getMinute();
            }

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String xhour = intToString(hourOfDay);
                    String xminute = intToString(minute);
                    timeN.setText(xhour + ":" + xminute);
                    sd.setTime(hourOfDay, minute);
                    mDatabase.child("user").child(u).child("settings").child("hour").setValue(hourOfDay);
                    mDatabase.child("user").child(u).child("settings").child("minute").setValue(minute);
                }
            }, nHour, nMinute, true);
            timePickerDialog.setTitle(R.string.time_for_notifications);
            timePickerDialog.setCancelable(true);
            timePickerDialog.show();
        } else if (v == restoreDef){
            sd.setDefault();
            mDatabase.child("user").child(u).child("settings").setValue(sd);
            setLocale(0);
            Intent def = new Intent(Settings.this, MainActivity.class);
            def.putExtra("u", u);
            startActivity(def);
        } else if (v == facReset){
            sd.setDefault();
            fd.deleteAll();
            mDatabase.child("user").child(u).child("friendlist").removeValue();
            removeImage();
            mDatabase.child("user").child(u).child("settings").setValue(sd);
            setLocale(0);
            Intent del = new Intent(Settings.this, MainActivity.class);
            del.putExtra("u", u);
            startActivity(del);
        } else if (v == msgTemp){
            Intent msg = new Intent(Settings.this, MessageTemplate.class);
            msg.putExtra("u", u);
            startActivity(msg);
        } else if (v == language){
            final int[] pilihan = new int[1];
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.language);
            builder.setSingleChoiceItems(lang, defLang, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    langN.setText(lang[which]);
                    pilihan[0] = which;
                }
            });
            builder.setPositiveButton(R.string.change, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sd.setLanguage(pilihan[0]);
                    mDatabase.child("user").child(u).child("settings").child("language").setValue(pilihan[0]);
                    setLocale(pilihan[0]);
                    dialog.dismiss();
                    refresh();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void zodiacSetter(){
        boolean c = sd.getChinese();
        mDatabase.child("user").child(u).child("settings").child("chinese").setValue(c);
        boolean w = sd.getWestern();
        mDatabase.child("user").child(u).child("settings").child("western").setValue(w);
    }

    private void setLocale(int x){
        String[] lang = new String[]{"en", "in"};
        String language = lang[x];
        locale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }
    private void refresh(){
        mDatabase.child("user").child(u).child("settings").setValue(sd);
        Intent refresh = new Intent(this, Settings.class);
        refresh.putExtra("u", u);
        startActivity(refresh);
    }

    private void removeImage() {
        String url = "friend_photos";
        StorageReference del = FirebaseStorage.getInstance().getReference().child(url);
        del.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Settings.this, getString(R.string.reset), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String intToString(int x){
        if (x % 10 > 0){
            return x + "";
        } else {
            return "0" + x;
        }
    }

    public void onCheckBoxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()){
            case R.id.set_on_birthday:
                if(checked) {
                    onBirthN.setText(R.string.notifshown);
                    sd.setZero(true);
                    mDatabase.child("user").child(u).child("settings").child("zero").setValue(true);
                } else {
                    onBirthN.setText(R.string.notifhidden);
                    sd.setZero(false);
                    mDatabase.child("user").child(u).child("settings").child("zero").setValue(false);
                }
                break;
            case R.id.set_one_day_before:
                if(checked) {
                    oneDayN.setText(R.string.notifshown);
                    sd.setOne(true);
                    mDatabase.child("user").child(u).child("settings").child("one").setValue(true);
                } else {
                    oneDayN.setText(R.string.notifhidden);
                    sd.setOne(false);
                    mDatabase.child("user").child(u).child("settings").child("one").setValue(false);
                }
                break;
            case R.id.set_two_days_before:
                if(checked) {
                    twoDayN.setText(R.string.notifshown);
                    sd.setTwo(true);
                    mDatabase.child("user").child(u).child("settings").child("two").setValue(true);
                } else {
                    twoDayN.setText(R.string.notifhidden);
                    sd.setTwo(false);
                    mDatabase.child("user").child(u).child("settings").child("two").setValue(false);
                }
                break;
            case R.id.set_seven_days_before:
                if(checked) {
                    sevenDayN.setText(R.string.notifshown);
                    sd.setSeven(true);
                    mDatabase.child("user").child(u).child("settings").child("seven").setValue(true);
                } else {
                    sevenDayN.setText(R.string.notifhidden);
                    sd.setSeven(false);
                    mDatabase.child("user").child(u).child("settings").child("seven").setValue(false);
                }
                break;
            case R.id.set_fourteen_days_before:
                if(checked) {
                    fourteenDayN.setText(R.string.notifshown);
                    sd.setFourteen(true);
                    mDatabase.child("user").child(u).child("settings").child("fourteen").setValue(true);
                } else {
                    fourteenDayN.setText(R.string.notifhidden);
                    sd.setFourteen(false);
                    mDatabase.child("user").child(u).child("settings").child("fourteen").setValue(false);
                }
                break;
        }
    }

    private void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.friend_list:
                Intent fl = new Intent(Settings.this, MainActivity.class);
                fl.putExtra("u", u);
                startActivity(fl);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getSettings(String u) {
        SettingsData data = new SettingsData();
        mDatabase.child("user").child(u).child("settings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sd = dataSnapshot.getValue(SettingsData.class);
                loadData();
//                Toast.makeText(Settings.this, sd.getZodiac() + "", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadData(){
        defZodiacSign = sd.getZodiac();
        zodiacSignN.setText(zodiac[defZodiacSign]);
        defLang = sd.getLanguage();
        langN.setText(lang[defLang]);
        sd.setTime(sd.getHour(), sd.getMinute());
        timeN.setText(sd.showTime());

        if (sd.getZero()){onBirthday.setChecked(true); onBirthN.setText(R.string.notifshown);}
        if (sd.getOne()){oneDay.setChecked(true); oneDayN.setText(R.string.notifshown);}
        if (sd.getTwo()){twoDay.setChecked(true); twoDayN.setText(R.string.notifshown);}
        if (sd.getSeven()){sevenDay.setChecked(true); sevenDayN.setText(R.string.notifshown);}
        if (sd.getFourteen()){fourteenDay.setChecked(true); fourteenDayN.setText(R.string.notifshown);}
    }
}
