package com.uc.contohbottomnav;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InfoPoint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_point);
        getSupportActionBar().setTitle("Points");
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
