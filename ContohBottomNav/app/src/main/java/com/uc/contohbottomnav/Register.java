package com.uc.contohbottomnav;

import android.content.Intent;
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

public class Register extends AppCompatActivity {

    EditText emailreg;
    EditText namain;
    EditText passreg;
    EditText cpassreg;
    Button btnSign;
    TextView login;
    ProgressBar progressreg;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Sign up");
        emailreg = findViewById(R.id.emailreg);
        namain = findViewById(R.id.namain);
        passreg = findViewById(R.id.passreg);
        cpassreg = findViewById(R.id.cpassreg);
        btnSign = findViewById(R.id.btnSign);
        login = findViewById(R.id.login);
        progressreg = findViewById(R.id.progressreg);

        progressreg.setVisibility(View.INVISIBLE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnSign.setVisibility(View.INVISIBLE);
                progressreg.setVisibility(View.VISIBLE);

                final String email = emailreg.getText().toString();
                final String nama = namain.getText().toString();
                final String pass1 = passreg.getText().toString();
                final String pass2 = cpassreg.getText().toString();

                if (email.isEmpty() || nama.isEmpty() || pass1.isEmpty() || pass2.isEmpty()){
                    showMessage("Please fill all fields!");
                    btnSign.setVisibility(View.VISIBLE);
                    progressreg.setVisibility(View.INVISIBLE);
                }else{
                    if (!pass1.equals(pass2)){
                        showMessage("Password not match");
                        btnSign.setVisibility(View.VISIBLE);
                        progressreg.setVisibility(View.INVISIBLE);
                    }
                    else{
                        createAccount(email, pass1, nama);
                    }
                }
            }
        });
    }

    private void createAccount(String email, String pass, String nama){
        requestQueue = Volley.newRequestQueue(Register.this);
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", pass);
        params.put("nama", nama);
        JSONObject parameters = new JSONObject(params);
        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://fusionsvisual.com/plastic/register.php", parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("reg");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                String hsl = jsonObject.getString("msg");
                                if(hsl.equalsIgnoreCase("Email already registered")){
                                    showMessage(hsl);
                                    btnSign.setVisibility(View.VISIBLE);
                                    progressreg.setVisibility(View.INVISIBLE);
                                }else if(hsl.equalsIgnoreCase("Signup failed!")){
                                    showMessage(hsl);
                                    btnSign.setVisibility(View.VISIBLE);
                                    progressreg.setVisibility(View.INVISIBLE);
                                }else if (hsl.equalsIgnoreCase("Please try again later")){
                                    showMessage(hsl);
                                    btnSign.setVisibility(View.VISIBLE);
                                    progressreg.setVisibility(View.INVISIBLE);
                                }else{
                                    showMessage(hsl);
                                    Intent intent = new Intent(Register.this, Login.class);
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

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Register.this, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}
