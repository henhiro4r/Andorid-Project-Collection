package com.uc.contohbottomnav;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class WithdrawScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    RequestQueue requestQueue;
    private String desc = "", userid = "", trashid = "", point = "";
    ActionBar myBar;
    SharedPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_scanner);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        myBar = getSupportActionBar();
        myBar.setTitle("Withdraw");
        userPref = getSharedPreferences("user", MODE_PRIVATE);
        userid = userPref.getString("userid","-");
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }


    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        trashid = result.getText();
        //Toast.makeText(this, trashid, Toast.LENGTH_SHORT).show();

        requestQueue = Volley.newRequestQueue(WithdrawScanner.this);
        Map<String, String> params = new HashMap<>();
        params.put("trashid", trashid);
        JSONObject parameters = new JSONObject(params);
        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://fusionsvisual.com/plastic/withdraw.php", parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("trash");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                String hsl = jsonObject.getString("msg");

                                if(hsl.equalsIgnoreCase("Trash is not registered")){
                                    Toast.makeText(WithdrawScanner.this, hsl, Toast.LENGTH_SHORT).show();
                                    mScannerView.resumeCameraPreview(WithdrawScanner.this);
                                }else{
                                    mScannerView.stopCamera();
                                    Intent intent = new Intent(WithdrawScanner.this, SelectAsset.class);
                                    intent.putExtra("idtrash", trashid);
                                    intent.putExtra("loc", hsl);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        requestQueue.add(jor);



    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        mScannerView.stopCamera();
        Intent intent = new Intent(WithdrawScanner.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
