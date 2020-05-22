package com.uc.contohbottomnav;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class Login extends AppCompatActivity {

    TextView signup;
    EditText emailinput;
    EditText passinput;
    TextView fpass;
    Button logbtn;
    ProgressBar loginbar;
    RequestQueue requestQueue;
    SharedPreferences userPref;
    SharedPreferences.Editor editor;

    private static final int MY_CAMERA_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Sign in");
        signup = findViewById(R.id.signup);
        emailinput = findViewById(R.id.emailinput);
        passinput = findViewById(R.id.passinput);
        fpass = findViewById(R.id.fpass);
        logbtn = findViewById(R.id.logbtn);
        loginbar = findViewById(R.id.loginbar);
        userPref = getSharedPreferences("user", MODE_PRIVATE);
        editor = userPref.edit();

        loginbar.setVisibility(View.INVISIBLE);

        fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, ForgotPassword.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logbtn.setVisibility(View.INVISIBLE);
                loginbar.setVisibility(View.VISIBLE);
                final String email = emailinput.getText().toString();
                final String password = passinput.getText().toString();

                if (email.isEmpty() || password.isEmpty()){
                    showMessage("Please fill all fields!");
                    logbtn.setVisibility(View.VISIBLE);
                    loginbar.setVisibility(View.INVISIBLE);
                }else{
                    checkAccount(email,password);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_CAMERA_REQUEST_CODE);

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }//end onRequestPermissionsResult

    private void checkAccount(String email, String pass) {
        requestQueue = Volley.newRequestQueue(Login.this);
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", pass);
        JSONObject parameters = new JSONObject(params);
        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, "https://fusionsvisual.com/plastic/login.php", parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hasil = null;
                            try {
                                hasil = response.getJSONArray("user");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < hasil.length(); i++) {
                                JSONObject jsonObject = hasil.getJSONObject(i);
                                String hsl = jsonObject.getString("msg");
//                                String mail = jsonObject.getString("email");
//                                String nama = jsonObject.getString("nama");
//                                //String password = jsonObject.getString("password");
//                                String iduser = jsonObject.getString("iduser");
                                if(hsl.equalsIgnoreCase("Please, validate account")){
                                    showMessage("Please validate your account");
                                    logbtn.setVisibility(View.VISIBLE);
                                    loginbar.setVisibility(View.INVISIBLE);
                                }else if(hsl.equalsIgnoreCase("Account not exist!")){
                                    showMessage("Incorrect email or password");
                                    logbtn.setVisibility(View.VISIBLE);
                                    loginbar.setVisibility(View.INVISIBLE);
                                }else if(hsl.equalsIgnoreCase("Login Failed!")){
                                    showMessage(hsl);
                                    logbtn.setVisibility(View.VISIBLE);
                                    loginbar.setVisibility(View.INVISIBLE);
                                }else if(hsl.equalsIgnoreCase("Welcome Back!")){
//                                    String mail = jsonObject.getString("email");
//                                    String nama = jsonObject.getString("nama");
//                                    //String password = jsonObject.getString("password");
//                                    String iduser = jsonObject.getString("iduser");
//                                    if (iduser.isEmpty() || mail.isEmpty() || nama.isEmpty()){
                                        showMessage("Account in use");
                                        logbtn.setVisibility(View.VISIBLE);
                                        loginbar.setVisibility(View.INVISIBLE);
//                                    }else{
//                                        editor.putString("userid",iduser);
//                                        editor.putString("email", mail);
//                                        editor.putString("nama",nama);
//                                        //editor.putString("password", password);
//                                        editor.commit();
//                                        Intent intent = new Intent(Login.this, MainActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        startActivity(intent);
//                                        finish();
//                                        showMessage("Welcome, "+userPref.getString("nama","-"));
//                                    }
                                }else{
                                    String mail = jsonObject.getString("email");
                                    String nama = jsonObject.getString("nama");
                                    //String password = jsonObject.getString("password");
                                    String iduser = jsonObject.getString("iduser");
                                    editor.putString("userid",iduser);
                                    editor.putString("email", mail);
                                    editor.putString("nama",nama);
                                    //editor.putString("password", password);
                                    editor.commit();
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                    showMessage(hsl+", "+userPref.getString("nama","-"));
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

    public boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onResume() {
        super.onResume();
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(a);
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(Login.this, "Press again to exit", Toast.LENGTH_SHORT).show();
    }

    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }
}