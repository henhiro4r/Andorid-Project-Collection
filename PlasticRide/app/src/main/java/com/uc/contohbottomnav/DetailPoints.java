package com.uc.contohbottomnav;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.uc.contohbottomnav.adapter.DetailPointsAdapter;
import com.uc.contohbottomnav.adapter.HistoryAdapter;
import com.uc.contohbottomnav.model.DetailPoint;
import com.uc.contohbottomnav.model.Trans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailPoints extends AppCompatActivity {

    private RecyclerView rvdetailpoints;
    ActionBar myBar;
    int iduser;
    RequestQueue requestQueue;

    ArrayList<DetailPoint> listDetailPoint = new ArrayList<>();

    private String idbottle;
    private String action;
    private String desc;
    private String time;
    private String point;
    SharedPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_points);
        rvdetailpoints = findViewById(R.id.rv_detail_points);
        userPref = getSharedPreferences("user", Context.MODE_PRIVATE);
        iduser = Integer.parseInt(userPref.getString("userid","-"));

        myBar = getSupportActionBar();
        myBar.setTitle("Detail Points");

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadDataAwal(iduser);
    }

    @Override
    public void onBackPressed() {
        //Intent i = new Intent(DetailPoints.this, MainActivity.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(i);
        finish();
    }

    public void loadDataAwal(int id){
        listDetailPoint.clear();
        rvdetailpoints.setAdapter(null);
        requestQueue = Volley.newRequestQueue(DetailPoints.this);
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        JSONObject parameters = new JSONObject(params);
        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://fusionsvisual.com/plastic/detailpoint.php", parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("detailpoint");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                action = jsonObject.getString("action");
                                time = jsonObject.getString("time");
                                desc = jsonObject.getString("desc");
                                idbottle = jsonObject.getString("idbottle");
                                point = jsonObject.getString("point");

                                DetailPoint d = new DetailPoint(idbottle,action,desc,time,point);
                                listDetailPoint.add(d);

                            }
                            showHistory(listDetailPoint);

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

    public void showHistory(ArrayList<DetailPoint> list){
        rvdetailpoints.setLayoutManager(new LinearLayoutManager(DetailPoints.this));
        DetailPointsAdapter detailPointsAdapter = new DetailPointsAdapter(DetailPoints.this);
        detailPointsAdapter.setListDetailPoint(list);
        rvdetailpoints.setAdapter(detailPointsAdapter);
    }
}
