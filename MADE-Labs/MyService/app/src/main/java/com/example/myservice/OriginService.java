package com.example.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.lang.ref.WeakReference;

public class OriginService extends Service implements DummyAsyncCallback{

    public static final String ORIGIN_SERVICE = "OriginService";

    public OriginService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(ORIGIN_SERVICE, "onStartCommand: Origin Service Dijalankan");

        DummyAsync dummyAsync = new DummyAsync(this);
        dummyAsync.execute();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(ORIGIN_SERVICE, "onDestroy: ");
    }

    @Override
    public void preAsync() {
        Log.d(ORIGIN_SERVICE, "preAsync: Start....");
    }

    @Override
    public void postAsync() {
        Log.d(ORIGIN_SERVICE, "postAsync: Finish....");
        stopSelf();
    }

    private static class DummyAsync extends AsyncTask<Void, Void, Void>{
        WeakReference<DummyAsyncCallback> callback;

        DummyAsync(DummyAsyncCallback callback) {
            this.callback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(ORIGIN_SERVICE, "onPreExecute: ");
            callback.get().preAsync();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(ORIGIN_SERVICE, "doInBackground: ");
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(ORIGIN_SERVICE, "onPostExecute: ");
            callback.get().postAsync();
        }
    }
}

interface DummyAsyncCallback{
    void preAsync();
    void postAsync();
}
