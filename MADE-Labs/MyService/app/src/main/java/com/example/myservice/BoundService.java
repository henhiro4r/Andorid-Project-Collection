package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class BoundService extends Service {

    final String TAG = BoundService.class.getSimpleName();
    MyBinder myBinder = new MyBinder();
    final long starttime = System.currentTimeMillis();

    CountDownTimer timer = new CountDownTimer(100000, 1000) {
        @Override
        public void onTick(long l) {
            long elapsedTime = System.currentTimeMillis() - starttime;
            Log.d(TAG, "onTick: " + elapsedTime);
        }

        @Override
        public void onFinish() {

        }
    };

    public BoundService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        timer.start();
        return myBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        timer.cancel();
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind: ");
    }

    class MyBinder extends Binder {
        BoundService getService(){
            return BoundService.this;
        }
    }
}
