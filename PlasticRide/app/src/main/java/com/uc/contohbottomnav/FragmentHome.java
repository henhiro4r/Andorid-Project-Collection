package com.uc.contohbottomnav;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {

    public FragmentHome() {
        // Required empty public constructor
    }

    private TextView totalasset, totaldeposit, totalpoint;
    private CardView cvdeposit, cvwithdraw, cvride, cvupcoming;
    private Button btndetailasset, btndetailpoint;

    RequestQueue requestQueue;
    private String mtotaldeposit = "", mtotalassets = "", mtotalpoint = "";
    private int iduser;

    private AlphaAnimation btnKlik = new AlphaAnimation(1F, 0.6F);
    private ZXingScannerView mScannerView;

    SharedPreferences userPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        totalasset = v.findViewById(R.id.txt_jumlah_botol);
        totaldeposit = v.findViewById(R.id.txt_total_deposit);
        totalpoint = v.findViewById(R.id.txt_jumlah_point);
        cvdeposit = v.findViewById(R.id.cv_deposit_home);
        cvwithdraw = v.findViewById(R.id.cv_withdraw_home);
        cvride = v.findViewById(R.id.cv_ride_home);
        cvupcoming = v.findViewById(R.id.cv_upcoming);
        btndetailasset = v.findViewById(R.id.btn_view_details_assets);
        btndetailpoint = v.findViewById(R.id.btn_view_details_points);
        userPref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        iduser = Integer.parseInt(userPref.getString("userid","-"));


        btndetailasset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);
                Intent i = new Intent(getActivity(), DetailAssets.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                //getActivity().finish();
            }
        });

        btndetailpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);
                Intent i = new Intent(getActivity(), DetailPoints.class);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                //getActivity().finish();
            }
        });

        cvdeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);
                Intent i = new Intent(getActivity(), DepositScanner.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                getActivity().finish();
            }
        });

        cvwithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);
                Intent i = new Intent(getActivity(), WithdrawScanner.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                getActivity().finish();
            }
        });

        cvride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);
                Intent i = new Intent(getActivity(), RideScanner.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                getActivity().finish();
            }
        });

        cvupcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);
                Toast.makeText(getActivity(),"Upcoming features",Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadDataAwal(iduser);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataAwal(iduser);
        //getFragmentManager().beginTransaction().detach(this).attach(this).commit();
    }

    public void loadDataAwal(int id){
        requestQueue = Volley.newRequestQueue(getActivity());
        //requestQueue.getCache().clear();
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        JSONObject parameters = new JSONObject(params);
        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://fusionsvisual.com/plastic/home.php", parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("home");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                mtotalassets = jsonObject.getString("totalassets");
                                mtotaldeposit = jsonObject.getString("totaldeposit");
                                mtotalpoint = jsonObject.getString("totalpoint");

                                totalasset.setText(mtotalassets);
                                totaldeposit.setText("Total deposit: "+mtotaldeposit+" unit");
                                totalpoint.setText(mtotalpoint);
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
