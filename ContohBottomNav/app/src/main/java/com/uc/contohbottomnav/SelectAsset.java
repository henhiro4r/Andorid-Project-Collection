package com.uc.contohbottomnav;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.uc.contohbottomnav.adapter.HistoryAdapter;
import com.uc.contohbottomnav.adapter.SelectAssetAdapter;
import com.uc.contohbottomnav.model.Selectasset;
import com.uc.contohbottomnav.model.Trans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SelectAsset extends AppCompatActivity {

    private TextView txtTotalPoint;
    //private Button btnwithdrawall;
    private ImageView back;
    private RecyclerView rvchooseasset;

    String idtrash = "", loc = "";
    ActionBar myBar;

    RequestQueue requestQueue;
    private String mpoint = "", mtotalpoint = "", midbottle = "", mdesc ="";
    private int iduser;

    private AlphaAnimation btnKlik = new AlphaAnimation(1F, 0.6F);
    private ZXingScannerView mScannerView;
    ArrayList<Selectasset> listSelAsset = new ArrayList<>();
    SharedPreferences userPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_asset);
        myBar = getSupportActionBar();
        myBar.setTitle("Choose Assets");
        Intent i = getIntent();
        idtrash = i.getStringExtra("idtrash");
        loc = i.getStringExtra("loc");
        txtTotalPoint = findViewById(R.id.txt_point_selasset);
        //btnwithdrawall = findViewById(R.id.btn_all_selasset);
        back = findViewById(R.id.img_back_selasset);
        rvchooseasset = findViewById(R.id.rv_choose_asset);
        userPref = getSharedPreferences("user", Context.MODE_PRIVATE);
        iduser = Integer.parseInt(userPref.getString("userid","-"));

        /*btnwithdrawall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);

            }
        });*/

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);
                Intent intent = new Intent(SelectAsset.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        //Toast.makeText(this, idtrash+"-"+loc, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadDataAwal(iduser);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SelectAsset.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void loadDataAwal(int id){
        listSelAsset.clear();
        rvchooseasset.setAdapter(null);
        requestQueue = Volley.newRequestQueue(SelectAsset.this);
        //requestQueue.getCache().clear();
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        JSONObject parameters = new JSONObject(params);
        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://fusionsvisual.com/plastic/getassets.php", parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            JSONArray hasil2 = null;
                            try {
                                hasil = response.getJSONArray("listassets");
                                //hasil2 = response.getJSONArray("totalpoin");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(hasil.length()==0){
                                txtTotalPoint.setText("-");
                                new AlertDialog.Builder(SelectAsset.this)
                                        .setTitle("Confirmation")
                                        .setIcon(R.drawable.cek)
                                        .setMessage("You don't have any bottle in your assets! Get more assets!")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(SelectAsset.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .create()
                                        .show();
                            }else{
                                for (int i = 0; i < hasil.length(); i++) {
                                    JSONObject jsonObject = hasil.getJSONObject(i);
                                    mtotalpoint = jsonObject.getString("total");
                                    midbottle = jsonObject.getString("idbottle");
                                    mdesc = jsonObject.getString("desc");
                                    mpoint = jsonObject.getString("point");

                                    Selectasset s = new Selectasset(midbottle,mdesc,mpoint);
                                    listSelAsset.add(s);

                                }
                                txtTotalPoint.setText(mtotalpoint);
                                showChooseAsset(listSelAsset);
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

    public void showChooseAsset(final ArrayList<Selectasset> list){
        rvchooseasset.setLayoutManager(new LinearLayoutManager(SelectAsset.this));
        SelectAssetAdapter selectAssetAdapter = new SelectAssetAdapter(SelectAsset.this);
        selectAssetAdapter.setListSelAsset(list);
        rvchooseasset.setAdapter(selectAssetAdapter);

        ItemClickSupport.addTo(rvchooseasset).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                v.startAnimation(btnKlik);
                Selectasset sel = list.get(position);
                requestQueue = Volley.newRequestQueue(SelectAsset.this);
                //requestQueue.getCache().clear();
                Map<String, String> params = new HashMap<>();
                params.put("bottleid",sel.getIdbottle());
                params.put("point",sel.getPoin());
                params.put("trashid",idtrash);
                params.put("desc",loc);
                params.put("userid", iduser+"");
                JSONObject parameters = new JSONObject(params);
                final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://fusionsvisual.com/plastic/withdrawprocess.php", parameters,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray hasil = null;
                                    try {
                                        hasil = response.getJSONArray("withdrawpro");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    for (int i = 0; i < hasil.length(); i++) {
                                        JSONObject jsonObject = hasil.getJSONObject(i);
                                        String hsl = jsonObject.getString("msg");

                                        if(hsl.equalsIgnoreCase("Bottle not exist!")){
                                            Toast.makeText(SelectAsset.this, hsl, Toast.LENGTH_SHORT).show();
                                        }else if(hsl.equalsIgnoreCase("Already withdrawed")){
                                            Toast.makeText(SelectAsset.this, hsl, Toast.LENGTH_SHORT).show();
                                        }else if(hsl.equalsIgnoreCase("Withdraw failed")){
                                            Toast.makeText(SelectAsset.this, hsl, Toast.LENGTH_SHORT).show();
                                        }else{
                                            loadDataAwal(iduser);
                                            //recreate();
                                            /*Intent intent = getIntent();
                                            finish();
                                            startActivity(intent);*/
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
        });

    }

}
