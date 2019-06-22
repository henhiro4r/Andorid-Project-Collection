package com.uc.contohbottomnav;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAccount extends Fragment {

    CardView about;
    CardView changepass;
    TextView editbtn;

    private AlphaAnimation btnKlik = new AlphaAnimation(1F, 0.6F);
    TextView logout, txtName, txtEmail, editName;

    RequestQueue requestQueue;
    SharedPreferences userPref;
    SharedPreferences.Editor editor;
    String iduser, email, nama;

    public FragmentAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_account, container, false);
        about = view.findViewById(R.id.cv_about);
        changepass = view.findViewById(R.id.cv_change_pass);
        logout = view.findViewById(R.id.txt_logout);
        txtName = view.findViewById(R.id.name_account);
        txtEmail = view.findViewById(R.id.email_account);
        editName = view.findViewById(R.id.editbtn);
        userPref = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        iduser = userPref.getString("userid","-");
        nama = userPref.getString("nama","-");
        email = userPref.getString("email","-");
        txtName.setText(nama);
        txtEmail.setText(email);
        editor = userPref.edit();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);
                logout.setEnabled(false);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Confirmation")
                        .setIcon(R.drawable.cek)
                        .setMessage("Are you sure to logout from PlasticRide, "+nama+"?")
                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                logout(email);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                logout.setEnabled(true);
                            }
                        })
                        .create()
                        .show();


            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);
                String action;
                Intent i  = new Intent(getActivity(), About.class);
                startActivity(i);
            }
        });

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);
                String action;
                Intent i  = new Intent(getActivity(), ChangePassword.class);
                startActivity(i);
            }
        });

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnKlik);
                String action;
                Intent i  = new Intent(getActivity(), EditNama.class);
                startActivity(i);
            }
        });

        return view;
    }

    private void editNameAccoun(){

    }


    private void logout(String mail){
        requestQueue = Volley.newRequestQueue(getActivity());
        Map<String, String> params = new HashMap<>();
        params.put("email", mail);
        JSONObject parameters = new JSONObject(params);
        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://fusionsvisual.com/plastic/logout.php", parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("out");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                String hsl = jsonObject.getString("msg");
                                Toast.makeText(getActivity(),hsl,Toast.LENGTH_SHORT).show();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(getActivity(), Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                getActivity().finish();
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
