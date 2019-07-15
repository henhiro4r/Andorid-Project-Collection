package com.example.myservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStartService, btnStartIntentService, btnStartBoundService, btnStopBoundService;
    Boolean serviceBound = false;
    BoundService boundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = findViewById(R.id.btn_start_service);
        btnStartIntentService = findViewById(R.id.btn_start_intent_service);
        btnStartBoundService = findViewById(R.id.btn_start_bound_service);
        btnStopBoundService = findViewById(R.id.btn_stop_bound_service);

        btnStartService.setOnClickListener(this);
        btnStartIntentService.setOnClickListener(this);
        btnStartBoundService.setOnClickListener(this);
        btnStopBoundService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.btn_start_service:
                intent = new Intent(MainActivity.this, OriginService.class);
                startService(intent);
                break;
            case R.id.btn_start_intent_service:
                intent = new Intent(MainActivity.this, DicodingIntentService.class);
                intent.putExtra(DicodingIntentService.EXTRA_DURATION, 5000);
                startService(intent);
                break;
            case R.id.btn_start_bound_service:
                intent = new Intent(MainActivity.this, BoundService.class);
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop_bound_service:
                unbindService(serviceConnection);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound){
            unbindService(serviceConnection);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BoundService.MyBinder myBinder = (BoundService.MyBinder) iBinder;
            boundService = myBinder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceBound = false;
        }
    };
}
