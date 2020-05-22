package com.uc.contohbottomnav;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
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

public class ForgotPassword extends AppCompatActivity {

    EditText emailfpass;
    EditText passfpass;
    EditText passfpass2;
    Button fpassbtn;
    ProgressBar progressfpass;
    RequestQueue requestQueue;
    SharedPreferences userPref;
//    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().setTitle("Forgot Password");

        emailfpass = findViewById(R.id.emailfpass);
        passfpass = findViewById(R.id.changepass);
        passfpass2 = findViewById(R.id.changepass2);
        fpassbtn = findViewById(R.id.changepassbtn);
        progressfpass = findViewById(R.id.progressfpass);
        userPref = getSharedPreferences("user", MODE_PRIVATE);
//        userid = userPref.getString("userid","-");

        progressfpass.setVisibility(View.INVISIBLE);

        fpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressfpass.setVisibility(View.VISIBLE);
                fpassbtn.setVisibility(View.INVISIBLE);
                final String email = emailfpass.getText().toString();
                final String password = passfpass.getText().toString();
                final String password2 = passfpass2.getText().toString();

                if (email.isEmpty() || password.isEmpty() || password2.isEmpty()){
                    showMessage("Please fill all fields!");
                    fpassbtn.setVisibility(View.VISIBLE);
                    progressfpass.setVisibility(View.INVISIBLE);
                }else{
                    if (!password.equals(password2)){
                        showMessage("Password not match");
                        fpassbtn.setVisibility(View.VISIBLE);
                        progressfpass.setVisibility(View.INVISIBLE);
                    }
                    else{
                        changePassword(email, password);
                    }
                }
            }
        });
    }

    private void changePassword(String email, String password){
        requestQueue = Volley.newRequestQueue(ForgotPassword.this);
        Map<String, String> params = new HashMap<>();
        params.put("id", "");
        params.put("email", email);
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
                                    fpassbtn.setVisibility(View.VISIBLE);
                                    progressfpass.setVisibility(View.INVISIBLE);
                                } else if(hsl.equalsIgnoreCase("Email not registered!")){
                                    showMessage(hsl);
                                    fpassbtn.setVisibility(View.VISIBLE);
                                    progressfpass.setVisibility(View.INVISIBLE);
                                }else{
                                    showMessage(hsl);
                                    Intent intent = new Intent(ForgotPassword.this, Login.class);
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

    @Override
    public void onBackPressed() {
        Intent a = new Intent(ForgotPassword.this, Login.class);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(a);
        finish();
    }

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}
