package com.uc.contohbottomnav;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InfoAssets extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_assets);
        getSupportActionBar().setTitle("Assets");
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
