package com.example.aurigaaristowijaya.birthday;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView list_birthday;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Friend> list = new ArrayList<>();
    private SettingsData sd = new SettingsData();
    private ConstraintLayout list_empty;
    private FriendData fd = new FriendData();
    private String u;
    private Locale locale;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        u = getIntent().getStringExtra("u");

        setActionBarTitle("Birthdays");

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        list_empty = (ConstraintLayout) findViewById(R.id.empty_list);
        list_birthday = (RecyclerView) findViewById(R.id.list_birthday);
        list_birthday.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSettings(u);
        getData(u);
    }

    private void showFriendBirthdays() {
        FriendData.setData(list);
        if (!list.isEmpty()) {
            list_empty.setVisibility(View.GONE);
            list_birthday.setVisibility(View.VISIBLE);

            list_birthday.setLayoutManager(new LinearLayoutManager(this));
            BirthdayList birthList = new BirthdayList(this);
            birthList.setListFriend(list);
            list_birthday.setAdapter(birthList);

            ItemClickSupport.addTo(list_birthday).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    viewFriendProfile(list.get(position));
                }
            });
        } else {
            list_birthday.setVisibility(View.GONE);
            list_empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_friend:
//                mDatabase.child("user").child(u).child("settings").setValue(sd);
                Intent addFriend = new Intent(MainActivity.this, AddNewFriend.class);
                addFriend.putExtra("id", "new");
                addFriend.putExtra("u", u);
                startActivity(addFriend);
                break;
            case R.id.settings:
//                mDatabase.child("user").child(u).child("settings").setValue(sd);
                Intent settings = new Intent(MainActivity.this, Settings.class);
                settings.putExtra("u", u);
                startActivity(settings);
                break;
            case R.id.log_out:
                mAuth.signOut();
                Intent log = new Intent(MainActivity.this, Login.class);
                startActivity(log);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void viewFriendProfile(Friend friend) {
        Intent viewFriend = new Intent(MainActivity.this, ViewFriend.class);
        String id = friend.getId();
        viewFriend.putExtra("id", id);
        viewFriend.putExtra("u", u);
        startActivity(viewFriend);
    }

    public void getSettings(String u) {
        mDatabase.child("user").child(u).child("settings").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SettingsData sd = dataSnapshot.getValue(SettingsData.class);
                setLocale(sd.getLanguage());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void refresh(){
        Intent refresh = new Intent(this, MainActivity.class);
        refresh.putExtra("u", u);
        startActivity(refresh);
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

    private void getData(String u) {
        mDatabase.child("user").child(u).child("friendlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot datSnapshot : dataSnapshot.getChildren()) {
                    Friend f = datSnapshot.getValue(Friend.class);
                    list.add(f);
                }
                showFriendBirthdays();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
