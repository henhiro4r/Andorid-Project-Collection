package com.uc.contohbottomnav;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.uc.contohbottomnav.adapter.HistoryAdapter;
import com.uc.contohbottomnav.adapter.InboxAdapter;
import com.uc.contohbottomnav.model.Inbox;
import com.uc.contohbottomnav.model.Trans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentInbox extends Fragment {

    RecyclerView rvinbox;
    RequestQueue requestQueue;
    private String idmsg;
    private String title;
    private String content;
    private String time;
    private int userid;
    ArrayList<Inbox> listInbox = new ArrayList<>();
    SharedPreferences userPref;
    private AlphaAnimation btnKlik = new AlphaAnimation(1F, 0.6F);

    public FragmentInbox() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        rvinbox = view.findViewById(R.id.rv_inbox);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadDataAwal(userid);
    }

    public void loadDataAwal(int id){
        listInbox.clear();
        rvinbox.setAdapter(null);
        requestQueue = Volley.newRequestQueue(getActivity());
        Map<String, Integer> params = new HashMap<>();
        //params.put("id", id);
        //JSONObject parameters = new JSONObject(params);
        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://fusionsvisual.com/plastic/inbox.php", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("inboxz");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                idmsg = jsonObject.getString("id");
                                title = jsonObject.getString("title");
                                content = jsonObject.getString("content");
                                time = jsonObject.getString("senttime");

                                Inbox in = new Inbox(idmsg,title,content,time);
                                listInbox.add(in);

                            }

                            if (getActivity()!=null) {
                                showInbox(listInbox);
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

    public void showInbox(ArrayList<Inbox> list){
        rvinbox.setLayoutManager(new LinearLayoutManager(getActivity()));
        InboxAdapter inboxAdapter = new InboxAdapter(getActivity());
        inboxAdapter.setListInbox(list);
        rvinbox.setAdapter(inboxAdapter);

        ItemClickSupport.addTo(rvinbox).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                v.startAnimation(btnKlik);
                Inbox inbox = listInbox.get(position);
                new AlertDialog.Builder(getActivity())
                        .setTitle(inbox.getTitle())
                        .setIcon(R.drawable.cek)
                        .setMessage(inbox.getContent()+"\n"+inbox.getSenttime())
                        .setCancelable(false)
                        .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .create()
                        .show();
            }
        });

    }

}
