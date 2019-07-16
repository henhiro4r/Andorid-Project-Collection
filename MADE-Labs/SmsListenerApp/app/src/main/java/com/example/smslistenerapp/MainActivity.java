package com.example.smslistenerapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnPermission, btnDownload;
    public static final String ACTION_DOWNLOAD_STATUS = "download_status";
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPermission = findViewById(R.id.btn_permission);
        btnDownload = findViewById(R.id.btn_download);
        btnPermission.setOnClickListener(this);
        btnDownload.setOnClickListener(this);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(DownloadService.TAG, "Download Complete!");
                Toast.makeText(context, "Download Complete!", Toast.LENGTH_SHORT).show();
            }
        };
        IntentFilter intentFilter = new IntentFilter(ACTION_DOWNLOAD_STATUS);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_permission:
                PermissionManager.check(this, Manifest.permission.RECEIVE_SMS, SMS_REQUEST_CODE);
                break;
            case R.id.btn_download:
                Intent download = new Intent(this, DownloadService.class);
                startService(download);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    final int SMS_REQUEST_CODE = 101;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SMS_REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "SMS receiver permission granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "SMS receiver permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
