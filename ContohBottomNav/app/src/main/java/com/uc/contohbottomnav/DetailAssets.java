package com.uc.contohbottomnav;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailAssets extends AppCompatActivity {

    private TextView txtjumlahL, txtjumlahM, txtjumlahS;
    RequestQueue requestQueue;
    private String jumlah = "", desc = "";
    private int iduser;
    SharedPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_assets);

        getSupportActionBar().setTitle("Detail Assets");

        userPref = getSharedPreferences("user", Context.MODE_PRIVATE);
        iduser = Integer.parseInt(userPref.getString("userid","-"));

        txtjumlahL = findViewById(R.id.txt_total_large);
        txtjumlahM = findViewById(R.id.txt_total_medium);
        txtjumlahS = findViewById(R.id.txt_total_small);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadDataAwal(iduser);
    }

    @Override
    public void onBackPressed() {
        //Intent i = new Intent(DetailAssets.this, MainActivity.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(i);
        finish();
    }

    public void loadDataAwal(int id){
        requestQueue = Volley.newRequestQueue(DetailAssets.this);
        //requestQueue.getCache().clear();
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        JSONObject parameters = new JSONObject(params);
        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://fusionsvisual.com/plastic/detailasset.php", parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("assets");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                jumlah = jsonObject.getString("jumlah");
                                desc = jsonObject.getString("desc");
                                if(desc.equalsIgnoreCase("Large Bottle")){
                                    txtjumlahL.setText(jumlah);
                                }else if(desc.equalsIgnoreCase("Medium Bottle")){
                                    txtjumlahM.setText(jumlah);
                                }else if(desc.equalsIgnoreCase("Small Bottle")){
                                    txtjumlahS.setText(jumlah);
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

}