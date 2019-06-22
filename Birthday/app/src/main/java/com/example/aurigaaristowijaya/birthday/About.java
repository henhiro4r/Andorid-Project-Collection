package com.example.aurigaaristowijaya.birthday;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class About extends AppCompatActivity {
    private String u;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        u = getIntent().getStringExtra("u");

        setActionBarTitle(getString(R.string.about));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.friend_list:
                Intent fl = new Intent(About.this, MainActivity.class);
                fl.putExtra("u", u);
                startActivity(fl);
                break;
            case R.id.settings:
                Intent set = new Intent(About.this, Settings.class);
                set.putExtra("u", u);
                startActivity(set);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
