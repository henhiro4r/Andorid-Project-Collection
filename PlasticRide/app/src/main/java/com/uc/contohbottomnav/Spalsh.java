package com.uc.contohbottomnav;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class Spalsh extends AppCompatActivity {

    private ImageView iv;
    ActionBar myBar;
    SharedPreferences userPref;
    Intent i = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        myBar = getSupportActionBar();
        myBar.hide();

        userPref = getSharedPreferences("user", MODE_PRIVATE);

        if(userPref.getString("userid", "-").equalsIgnoreCase("-")){
            i = new Intent(getApplicationContext(),Login.class);
        }else{
            i = new Intent(getApplicationContext(),MainActivity.class);
            Toast.makeText(this, "Welcome back, "+userPref.getString("nama","-"), Toast.LENGTH_LONG).show();
        }

        iv = findViewById(R.id.imgSpalsh);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.transition);
        iv.startAnimation(anim);

        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {

                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }

}