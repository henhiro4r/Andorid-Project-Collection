package com.uc.contohbottomnav;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class DepositScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    RequestQueue requestQueue;
    private String desc = "", userid = "", bottleid = "", point = "";
    ActionBar myBar;
    SharedPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_scanner);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        myBar = getSupportActionBar();
        myBar.setTitle("Deposit");
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
        bottleid = result.getText();
        //Toast.makeText(this, bottleid, Toast.LENGTH_SHORT).show();
        try{
            if(bottleid.charAt(4) == 'A'){
                desc = "Large Bottle";
                point = "1000";
            }else if(bottleid.charAt(4) == 'B'){
                desc = "Medium Bottle";
                point = "600";
            }else if(bottleid.charAt(4) == 'C'){
                desc = "Small Bottle";
                point = "300";
            }
        }catch(Exception e){

        }

        requestQueue = Volley.newRequestQueue(DepositScanner.this);
        Map<String, String> params = new HashMap<>();
        params.put("bottleid", bottleid);
        params.put("userid", userid);
        params.put("desc", desc);
        params.put("point", point);
        JSONObject parameters = new JSONObject(params);
        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://fusionsvisual.com/plastic/deposit.php", parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("deposit");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                String hsl = jsonObject.getString("msg");

                                if(hsl.equalsIgnoreCase("Bottle not exist!")){
                                    showMessage("Bottle doesn't exist!");
                                    mScannerView.resumeCameraPreview(DepositScanner.this);
                                }else if(hsl.equalsIgnoreCase("Already claimed!")){
                                    Toast.makeText(DepositScanner.this, hsl, Toast.LENGTH_SHORT).show();
                                    mScannerView.resumeCameraPreview(DepositScanner.this);
                                }else if(hsl.equalsIgnoreCase("Asset not valid!")){
                                    Toast.makeText(DepositScanner.this, hsl, Toast.LENGTH_SHORT).show();
                                    mScannerView.resumeCameraPreview(DepositScanner.this);
                                }else if(hsl.equalsIgnoreCase("Deposit Failed!")){
                                    Toast.makeText(DepositScanner.this, hsl, Toast.LENGTH_SHORT).show();
                                    mScannerView.resumeCameraPreview(DepositScanner.this);
                                }else{
                                    mScannerView.stopCamera();
                                    Intent intent = new Intent(DepositScanner.this, MainActivity.class);
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

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onBackPressed() {
        mScannerView.stopCamera();
        Intent intent = new Intent(DepositScanner.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
