package com.uc.contohbottomnav;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InfoHow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_how);
        getSupportActionBar().setTitle("How To Use");
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
