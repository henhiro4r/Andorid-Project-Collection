package com.uc.contohbottomnav;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class ChangePassword extends AppCompatActivity {

    EditText changepass;
    EditText changepass2;
    Button changepassbtn;
    ProgressBar progresschpass;
    RequestQueue requestQueue;
    SharedPreferences userPref;
    SharedPreferences.Editor editor;
    String userid, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setTitle("Change Password");
        userPref = getSharedPreferences("user", Context.MODE_PRIVATE);
        userid = userPref.getString("userid","-");
        email = userPref.getString("email","-");
        editor = userPref.edit();

        changepass = findViewById(R.id.changepass);
        changepass2 = findViewById(R.id.changepass2);
        changepassbtn = findViewById(R.id.changepassbtn);
        progresschpass = findViewById(R.id.progresschpass);

        progresschpass.setVisibility(View.INVISIBLE);

        changepassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changepassbtn.setVisibility(View.INVISIBLE);
                progresschpass.setVisibility(View.VISIBLE);
                final String password = changepass.getText().toString();
                final String password2 = changepass2.getText().toString();

                if (password.isEmpty() || password2.isEmpty()){
                    showMessage("Please fill all fields!");
                    changepassbtn.setVisibility(View.VISIBLE);
                    progresschpass.setVisibility(View.INVISIBLE);
                }else{
                    if (!password.equals(password2)){
                        showMessage("Password not match");
                        changepassbtn.setVisibility(View.VISIBLE);
                        progresschpass.setVisibility(View.INVISIBLE);
                    }
                    else{
                        changePassword(password);
                    }
                }
            }
        });
    }

    private void changePassword(String password){
        String id = userid;
        requestQueue = Volley.newRequestQueue(ChangePassword.this);
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("email", "");
        params.put("password", password);
        JSONObject parameters = new JSONObject(params);
        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://fusionsvisual.com/plastic/password.php", parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("changed");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                String hsl = jsonObject.getString("msg");
                                if (hsl.equalsIgnoreCase("Password not changed, Try again!")) {
                                    showMessage(hsl);
                                    changepassbtn.setVisibility(View.VISIBLE);
                                    progresschpass.setVisibility(View.INVISIBLE);
                                } else if(hsl.equalsIgnoreCase("Email not registered!")){
                                    showMessage(hsl);
                                    changepassbtn.setVisibility(View.VISIBLE);
                                    progresschpass.setVisibility(View.INVISIBLE);
                                }else{
                                    showMessage(hsl);
                                    logout(email);
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
    public void onBackPressed() {
        finish();
    }

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    private void logout(String mail){
        requestQueue = Volley.newRequestQueue(ChangePassword.this);
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
                                //String hsl = jsonObject.getString("msg");
                                //Toast.makeText(ChangePassword.this,hsl,Toast.LENGTH_SHORT).show();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(ChangePassword.this, Login.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
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
