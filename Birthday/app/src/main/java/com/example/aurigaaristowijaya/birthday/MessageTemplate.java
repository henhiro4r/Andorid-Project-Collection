package com.example.aurigaaristowijaya.birthday;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessageTemplate extends AppCompatActivity implements View.OnClickListener{
    private EditText subject, body;
    private Button butSubject, butBody;
    private SettingsData sd;
    private String u;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_template);
        setActionBarTitle(getString(R.string.message_templates));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        u = getIntent().getStringExtra("u"); //user ID

        body = (EditText)findViewById(R.id.body_text);
        butBody = (Button)findViewById(R.id.save_body_text);
        butBody.setOnClickListener(this);
        subject = (EditText)findViewById(R.id.subject_text);
        butSubject = (Button)findViewById(R.id.save_subject_text);
        butSubject.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSettings(u);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.save_body_text:
                String b = body.getText().toString();
                sd.setMessageTemp(b);
                mDatabase.child("user").child(u).child("settings").child("messageTemp").setValue(b);
                Toast.makeText(this, getString(R.string.saveMsgTemp), Toast.LENGTH_SHORT).show();
                break;
            case R.id.save_subject_text:
                String s = subject.getText().toString();
                sd.setMessageSubject(s);
                mDatabase.child("user").child(u).child("settings").child("messageSubject").setValue(s);
                Toast.makeText(this, getString(R.string.saveMsgSub), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void getSettings(String u) {
        SettingsData data = new SettingsData();
        final SettingsData[] simpan = new SettingsData[1];
        mDatabase.child("user").child(u).child("settings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sd = dataSnapshot.getValue(SettingsData.class);
                loadData();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadData(){
        subject.setText(sd.getMessageSubject());
        body.setText(sd.getMessageTemp());
    }

    private void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_msg_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.friend_list:
//                mDatabase.child("user").child(u).child("settings").setValue(sd);
                Intent fl = new Intent(MessageTemplate.this, MainActivity.class);
                fl.putExtra("u", u);
                startActivity(fl);
                break;
            case R.id.settings:
//                mDatabase.child("user").child(u).child("settings").setValue(sd);
                Intent set = new Intent(MessageTemplate.this, Settings.class);
                set.putExtra("u", u);
                startActivity(set);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
