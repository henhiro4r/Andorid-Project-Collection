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

public class EditNama extends AppCompatActivity {

    ProgressBar chbar;
    EditText chname;
    Button chnamebtn;
    SharedPreferences userPref;
    SharedPreferences.Editor editor;
    String iduser, nama;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nama);

        chbar = findViewById(R.id.chbar);
        chname = findViewById(R.id.chname);
        chnamebtn = findViewById(R.id.chnamebtn);
        userPref = getSharedPreferences("user", Context.MODE_PRIVATE);
        iduser = userPref.getString("userid","-");
        nama = userPref.getString("nama","-");
        editor = userPref.edit();
        chname.setText(nama);

        chbar.setVisibility(View.INVISIBLE);

        chnamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chnamebtn.setVisibility(View.INVISIBLE);
                chbar.setVisibility(View.VISIBLE);
                final String names = chname.getText().toString();
                if (!nama.equalsIgnoreCase(names)){
                    changename(names);
                }else{
                    finish();
                }
            }
        });
    }

    private void changename(final String names) {
        requestQueue = Volley.newRequestQueue(EditNama.this);
        Map<String, String> params = new HashMap<>();
        params.put("id", iduser);
        params.put("nama", names);
        JSONObject parameters = new JSONObject(params);
        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://fusionsvisual.com/plastic/editname.php", parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                String hsl = jsonObject.getString("msg");
                                if (hsl.equalsIgnoreCase("Update Failed")){
                                    showMessage(hsl);
                                    chnamebtn.setVisibility(View.VISIBLE);
                                    chbar.setVisibility(View.INVISIBLE);
                                }else{
                                    showMessage(hsl);
                                    editor.putString("nama",names);
                                    editor.commit();
                                    Intent intent = new Intent(EditNama.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
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

    @Override
    public void onBackPressed() {
        finish();
    }

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
