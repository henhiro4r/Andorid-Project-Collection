package com.example.favorite;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.favorite.preference.Pref;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    private Pref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        pref = new Pref(this);
        loadLocale();
        ImageView splash = findViewById(R.id.iv_splash);
        splash.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_splash));
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(1200);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }

    private void loadLocale(){
        int lang = pref.getLangPreference();
        setLocale(lang);
    }

    private void setLocale(int i) {
        String[] lang = new String[]{"en", "in"};
        String language = lang[i];
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }
}
