package com.uc.contohbottomnav;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.uc.contohbottomnav.adapter.HistoryAdapter;
import com.uc.contohbottomnav.model.Trans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHistory extends Fragment {

    public FragmentHistory() {
        // Required empty public constructor
    }

    RequestQueue requestQueue;
    private String idtrans;
    private String iduser;
    private String action;
    private String time;
    private String desc;
    private String idbottle;
    private String idtrash;
    private String idbus;
    private String point;
    private int userid;
    ArrayList<Trans> listTrans = new ArrayList<>();
    private RecyclerView rvhistory;
    LinearLayoutManager layoutManager;
    SharedPreferences userPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_history, container, false);
        userPref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userid = Integer.parseInt(userPref.getString("userid","-"));

        rvhistory = v.findViewById(R.id.rv_history);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
       ;

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadDataAwal(userid);
    }

    public void loadDataAwal(int id){
        listTrans.clear();
        rvhistory.setAdapter(null);
        requestQueue = Volley.newRequestQueue(getActivity());
        Map<String, Integer> params = new HashMap<>();
        params.put("id", id);
        JSONObject parameters = new JSONObject(params);
        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://fusionsvisual.com/plastic/history.php", parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("history");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                idtrans = jsonObject.getString("idtrans");
                                iduser = jsonObject.getString("iduser");
                                action = jsonObject.getString("action");
                                time = jsonObject.getString("time");
                                desc = jsonObject.getString("desc");
                                idbottle = jsonObject.getString("idbottle");
                                idtrash = jsonObject.getString("idtrash");
                                idbus = jsonObject.getString("idbus");
                                point = jsonObject.getString("point");

                                Trans t = new Trans(idtrans,iduser,action,time,desc,idbottle,idtrash,idbus,point);
                                listTrans.add(t);

                            }

                            if (getActivity()!=null) {
                                showHistory(listTrans);
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

    public void showHistory(ArrayList<Trans> list){
        rvhistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        HistoryAdapter historyAdapter = new HistoryAdapter(getActivity());
        historyAdapter.setListTrans(list);
        rvhistory.setAdapter(historyAdapter);

        /*ItemClickSupport.addTo(rvNowPlaying).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                lm.clear();
                Intent i = new Intent(getActivity(), MovieDetail.class);
                Movies mov = listMov.get(position);
                lm.add(mov);
                i.putExtra("lm", lm);
                //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //getActivity().finish();
                startActivity(i);
            }
        });*/

    }

}
